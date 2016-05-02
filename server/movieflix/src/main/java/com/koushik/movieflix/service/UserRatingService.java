/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.service;

import com.koushik.movieflix.entity.Title;
import com.koushik.movieflix.entity.User;
import com.koushik.movieflix.entity.UserRating;
import com.koushik.movieflix.entity.UserRatingPK;
import com.koushik.movieflix.exception.TitleNotFoundException;
import com.koushik.movieflix.exception.UserNotFoundException;

/**
 *
 * @author koushik
 */
public interface UserRatingService {

    public UserRating findUserRating(UserRatingPK id) throws UserNotFoundException;
    
    public User findUser(int id) throws UserNotFoundException;
    
    public Title findTitle(int id) throws TitleNotFoundException;
    
    public void rate(UserRating rating) ;
}
