/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.service;

import com.koushik.movieflix.entity.User;
import com.koushik.movieflix.entity.UserRating;
import com.koushik.movieflix.exception.UserAlreadyExistsException;
import com.koushik.movieflix.exception.UserNotFoundException;

/**
 *
 * @author koushik
 */
public interface UserService {

    public User login(User user) throws UserNotFoundException;

    public void signup(User user) throws UserAlreadyExistsException;

    public boolean isUserExist(User user);
    
}
