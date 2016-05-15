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
import javax.persistence.EntityManager;
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
    private EntityManager em;

    @Override
    public void create(UserRating userRating) {
        em.persist(userRating);
    }

    @Override
    public void edit(UserRating userRating) {
        em.merge(userRating);
    }

    @Override
    public UserRating findUserRating(UserRatingPK id) {
        return em.find(UserRating.class, id);
    }

    @Override
    public User findUser(int id) {
        return em.find(User.class, id);
    }
    
    @Override
    public Title findTitle(int id) {
        return em.find(Title.class, id);
    }
    
    @Override
    public UserRating findUserRatingforTitle(int userId, int titleId) {
        UserRatingPK userRatingKey = new UserRatingPK(userId, titleId);
        return em.find(UserRating.class, userRatingKey);
    }

    public int getUserRatingCount() {

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
