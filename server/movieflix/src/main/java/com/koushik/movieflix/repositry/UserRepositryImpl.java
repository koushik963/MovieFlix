/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.repositry;

import com.koushik.movieflix.entity.User;
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
public class UserRepositryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void create(User user) {
        if (user.getUserRatingCollection() == null) {
            user.setUserRatingCollection(new ArrayList<UserRating>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            Collection<UserRating> attachedUserRatingCollection = new ArrayList<UserRating>();
            for (UserRating userRatingCollectionUserRatingToAttach : user.getUserRatingCollection()) {
                userRatingCollectionUserRatingToAttach = em.getReference(userRatingCollectionUserRatingToAttach.getClass(), userRatingCollectionUserRatingToAttach.getUserRatingPK());
                attachedUserRatingCollection.add(userRatingCollectionUserRatingToAttach);
            }
            user.setUserRatingCollection(attachedUserRatingCollection);
            em.persist(user);
            for (UserRating userRatingCollectionUserRating : user.getUserRatingCollection()) {
                User oldUserOfUserRatingCollectionUserRating = userRatingCollectionUserRating.getUser();
                userRatingCollectionUserRating.setUser(user);
                userRatingCollectionUserRating = em.merge(userRatingCollectionUserRating);
                if (oldUserOfUserRatingCollectionUserRating != null) {
                    oldUserOfUserRatingCollectionUserRating.getUserRatingCollection().remove(userRatingCollectionUserRating);
                    oldUserOfUserRatingCollectionUserRating = em.merge(oldUserOfUserRatingCollectionUserRating);
                }
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            User persistentUser = em.find(User.class, user.getId());
            Collection<UserRating> userRatingCollectionOld = persistentUser.getUserRatingCollection();
            Collection<UserRating> userRatingCollectionNew = user.getUserRatingCollection();
            List<String> illegalOrphanMessages = null;
            for (UserRating userRatingCollectionOldUserRating : userRatingCollectionOld) {
                if (!userRatingCollectionNew.contains(userRatingCollectionOldUserRating)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserRating " + userRatingCollectionOldUserRating + " since its user field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<UserRating> attachedUserRatingCollectionNew = new ArrayList<UserRating>();
            for (UserRating userRatingCollectionNewUserRatingToAttach : userRatingCollectionNew) {
                userRatingCollectionNewUserRatingToAttach = em.getReference(userRatingCollectionNewUserRatingToAttach.getClass(), userRatingCollectionNewUserRatingToAttach.getUserRatingPK());
                attachedUserRatingCollectionNew.add(userRatingCollectionNewUserRatingToAttach);
            }
            userRatingCollectionNew = attachedUserRatingCollectionNew;
            user.setUserRatingCollection(userRatingCollectionNew);
            user = em.merge(user);
            for (UserRating userRatingCollectionNewUserRating : userRatingCollectionNew) {
                if (!userRatingCollectionOld.contains(userRatingCollectionNewUserRating)) {
                    User oldUserOfUserRatingCollectionNewUserRating = userRatingCollectionNewUserRating.getUser();
                    userRatingCollectionNewUserRating.setUser(user);
                    userRatingCollectionNewUserRating = em.merge(userRatingCollectionNewUserRating);
                    if (oldUserOfUserRatingCollectionNewUserRating != null && !oldUserOfUserRatingCollectionNewUserRating.equals(user)) {
                        oldUserOfUserRatingCollectionNewUserRating.getUserRatingCollection().remove(userRatingCollectionNewUserRating);
                        oldUserOfUserRatingCollectionNewUserRating = em.merge(oldUserOfUserRatingCollectionNewUserRating);
                    }
                }
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UserRating> userRatingCollectionOrphanCheck = user.getUserRatingCollection();
            for (UserRating userRatingCollectionOrphanCheckUserRating : userRatingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the UserRating " + userRatingCollectionOrphanCheckUserRating + " in its userRatingCollection field has a non-nullable user field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    @Override
    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    @Override
    public List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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
    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public User findByEmail(User user) {
        EntityManager em = getEntityManager();
        List<User> users = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", user.getEmail()).getResultList();

        if (!users.isEmpty()) {
            return users.get(0);
        }

        return null;

    }

}
