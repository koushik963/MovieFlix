/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.repositry;

import com.koushik.movieflix.entity.User;
import com.koushik.movieflix.entity.UserRating;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    private EntityManager em;

    @Override
    public void create(User user) {
        if (user.getUserRatingCollection() == null) {
            user.setUserRatingCollection(new ArrayList<UserRating>());
        }
        try {
            Collection<UserRating> attachedUserRatingCollection = new ArrayList<>();
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
                    em.merge(oldUserOfUserRatingCollectionUserRating);
                }
            }
        } catch (Exception e) {
            System.out.println("Error while Creating User: " + e.getMessage());
        }
    }

    @Override
    public void edit(User user) {
        try {
            User persistentUser = em.find(User.class, user.getId());
            Collection<UserRating> userRatingCollectionOld = persistentUser.getUserRatingCollection();
            Collection<UserRating> userRatingCollectionNew = user.getUserRatingCollection();
            Collection<UserRating> attachedUserRatingCollectionNew = new ArrayList<>();
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
                        em.merge(oldUserOfUserRatingCollectionNewUserRating);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error while updating User");
        }
    }

    @Override
    public User findUser(Integer id) {
        try {
            return em.find(User.class, id);
        } catch (Exception e) {
            System.out.println("Error while Finding User: " + e.getMessage());
        }
        return null;
    }

    public int getUserCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } catch (Exception e) {
            System.out.println("Error while getting User count: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public User findByEmail(String email) {
        List<User> users = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getResultList();
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;

    }

}
