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
import com.koushik.movieflix.repositry.UserRatingRepositry;
import com.koushik.movieflix.repositry.UserRepository;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author koushik
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRatingRepositry userRatingRepository;

    @Override
    public User login(User user) throws UserNotFoundException {
        User persistedUser = userRepository.findByEmail(user.getEmail());
        if (persistedUser == null) {
            throw new UserNotFoundException();
        }
        return userRepository.findByEmail(user.getEmail());
    }

    @Override
    public void signup(User user) throws UserAlreadyExistsException {

        boolean userExist = isUserExist(user);
        if (userExist) {
            throw new UserAlreadyExistsException();
        }
        userRepository.create(user);
    }

    @Override
    public boolean isUserExist(User user) {

        User checkUserExit = userRepository.findByEmail(user.getEmail());
        return checkUserExit != null;
    }

}
