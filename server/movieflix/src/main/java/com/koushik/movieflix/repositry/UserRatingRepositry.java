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

/**
 *
 * @author koushik
 */
public interface UserRatingRepositry {

    public void create(UserRating userRating);

    public UserRating findUserRating(UserRatingPK id);
    
    public User findUser(int id);
    
    public Title findTitle(int id);
}
