/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.repositry;

import com.koushik.movieflix.entity.Title;
import com.koushik.movieflix.entity.User;
import com.koushik.movieflix.entity.UserRating;
import com.koushik.movieflix.entity.UserRatingPK;
import com.koushik.movieflix.exception.NonexistentEntityException;
import com.koushik.movieflix.exception.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 *
 * @author koushik
 */
@Repository
public class UserRatingRepositryImpl implements UserRatingRepositry {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void create(UserRating userRating) throws PreexistingEntityException, Exception {
        if (userRating.getUserRatingPK() == null) {
            userRating.setUserRatingPK(new UserRatingPK());
        }
        userRating.getUserRatingPK().setUserId(userRating.getUser().getId());
        userRating.getUserRatingPK().setTitleId(userRating.getTitle().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            User user = userRating.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getId());
                userRating.setUser(user);
            }
            Title title = userRating.getTitle();
            if (title != null) {
                title = em.getReference(title.getClass(), title.getId());
                userRating.setTitle(title);
            }
            em.persist(userRating);
        } catch (Exception ex) {
            if (findUserRating(userRating.getUserRatingPK()) != null) {
                throw new PreexistingEntityException("UserRating " + userRating + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(UserRating userRating) throws NonexistentEntityException, Exception {
        userRating.getUserRatingPK().setUserId(userRating.getUser().getId());
        userRating.getUserRatingPK().setTitleId(userRating.getTitle().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            UserRating persistentUserRating = em.find(UserRating.class, userRating.getUserRatingPK());
            User userOld = persistentUserRating.getUser();
            User userNew = userRating.getUser();
            Title titleOld = persistentUserRating.getTitle();
            Title titleNew = userRating.getTitle();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getId());
                userRating.setUser(userNew);
            }
            if (titleNew != null) {
                titleNew = em.getReference(titleNew.getClass(), titleNew.getId());
                userRating.setTitle(titleNew);
            }
            userRating = em.merge(userRating);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getUserRatingCollection().remove(userRating);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getUserRatingCollection().add(userRating);
                userNew = em.merge(userNew);
            }
            if (titleOld != null && !titleOld.equals(titleNew)) {
                titleOld.getUserRatingCollection().remove(userRating);
                titleOld = em.merge(titleOld);
            }
            if (titleNew != null && !titleNew.equals(titleOld)) {
                titleNew.getUserRatingCollection().add(userRating);
                titleNew = em.merge(titleNew);
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                UserRatingPK id = userRating.getUserRatingPK();
                if (findUserRating(id) == null) {
                    throw new NonexistentEntityException("The userRating with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void destroy(UserRatingPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            UserRating userRating;
            try {
                userRating = em.getReference(UserRating.class, id);
                userRating.getUserRatingPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userRating with id " + id + " no longer exists.", enfe);
            }
            User user = userRating.getUser();
            if (user != null) {
                user.getUserRatingCollection().remove(userRating);
                user = em.merge(user);
            }
            Title title = userRating.getTitle();
            if (title != null) {
                title.getUserRatingCollection().remove(userRating);
                title = em.merge(title);
            }
            em.remove(userRating);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<UserRating> findUserRatingEntities() {
        return findUserRatingEntities(true, -1, -1);
    }

    @Override
    public List<UserRating> findUserRatingEntities(int maxResults, int firstResult) {
        return findUserRatingEntities(false, maxResults, firstResult);
    }

    private List<UserRating> findUserRatingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserRating.class));
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
    public UserRating findUserRating(UserRatingPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserRating.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserRatingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserRating> rt = cq.from(UserRating.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
