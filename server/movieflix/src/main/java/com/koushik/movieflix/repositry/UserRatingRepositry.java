/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.repositry;

import com.koushik.movieflix.entity.UserRating;
import com.koushik.movieflix.entity.UserRatingPK;
import com.koushik.movieflix.exception.NonexistentEntityException;
import com.koushik.movieflix.exception.PreexistingEntityException;
import java.util.List;

/**
 *
 * @author koushik
 */
public interface UserRatingRepositry {

    public void create(UserRating userRating) throws PreexistingEntityException, Exception;

    public void edit(UserRating userRating) throws NonexistentEntityException, Exception;

    public void destroy(UserRatingPK id) throws NonexistentEntityException;

    public List<UserRating> findUserRatingEntities();

    public List<UserRating> findUserRatingEntities(int maxResults, int firstResult);

    public UserRating findUserRating(UserRatingPK id);
}
