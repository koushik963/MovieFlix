/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.repositry;

import com.koushik.movieflix.entity.Title;
import com.koushik.movieflix.entity.UserRating;
import com.koushik.movieflix.exception.IllegalOrphanException;
import com.koushik.movieflix.exception.NonexistentEntityException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

@Repository
public class TitleRepositryImpl implements TitleRepositry {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void create(Title title) {
        if (title.getUserRatingCollection() == null) {
            title.setUserRatingCollection(new ArrayList<UserRating>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            Collection<UserRating> attachedUserRatingCollection = new ArrayList<UserRating>();
            for (UserRating userRatingCollectionUserRatingToAttach : title.getUserRatingCollection()) {
                userRatingCollectionUserRatingToAttach = em.getReference(userRatingCollectionUserRatingToAttach.getClass(), userRatingCollectionUserRatingToAttach.getUserRatingPK());
                attachedUserRatingCollection.add(userRatingCollectionUserRatingToAttach);
            }
            title.setUserRatingCollection(attachedUserRatingCollection);
            em.persist(title);
            for (UserRating userRatingCollectionUserRating : title.getUserRatingCollection()) {
                Title oldTitleOfUserRatingCollectionUserRating = userRatingCollectionUserRating.getTitle();
                userRatingCollectionUserRating.setTitle(title);
                userRatingCollectionUserRating = em.merge(userRatingCollectionUserRating);
                if (oldTitleOfUserRatingCollectionUserRating != null) {
                    oldTitleOfUserRatingCollectionUserRating.getUserRatingCollection().remove(userRatingCollectionUserRating);
                    oldTitleOfUserRatingCollectionUserRating = em.merge(oldTitleOfUserRatingCollectionUserRating);
                }
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Title title) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Title persistentTitle = em.find(Title.class, title.getId());
            Collection<UserRating> userRatingCollection = persistentTitle.getUserRatingCollection();
            title.setUserRatingCollection(userRatingCollection);
            title = em.merge(title);

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Title title;
            try {
                title = em.getReference(Title.class, id);
                title.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The title with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UserRating> userRatingCollectionOrphanCheck = title.getUserRatingCollection();
            for (UserRating userRatingCollectionOrphanCheckUserRating : userRatingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Title (" + title + ") cannot be destroyed since the UserRating " + userRatingCollectionOrphanCheckUserRating + " in its userRatingCollection field has a non-nullable title field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(title);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Title> findTitleEntities() {
        return findTitleEntities(true, -1, -1);
    }

    @Override
    public List<Title> findTitleEntities(int maxResults, int firstResult) {
        return findTitleEntities(false, maxResults, firstResult);
    }

    @Override
    public List<Title> findTitleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Title.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Title findTitle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Title.class, id);
        } finally {
            em.close();
        }
    }

    public int getTitleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Title> rt = cq.from(Title.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
