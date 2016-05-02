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
import com.koushik.movieflix.repositry.UserRatingRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author koushik
 */
@Service
@Transactional
public class UserRatingServiceImpl implements UserRatingService {

    @Autowired
    UserRatingRepositry repository;

    @Override
    public UserRating findUserRating(UserRatingPK id) throws UserNotFoundException {
        UserRating userRating = repository.findUserRating(id);
        if (userRating == null) {
            throw new UserNotFoundException();
        }
        return userRating;
    }

    @Override
    public User findUser(int id) throws UserNotFoundException {
        User user = repository.findUser(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public Title findTitle(int id) throws TitleNotFoundException {
        Title title = repository.findTitle(id);
        if (title == null) {
            throw new TitleNotFoundException();
        }
        return title;
    }

    @Override
    public void rate(UserRating userRating) {
        if (userRating.getUserRatingPK() == null) {
            userRating.setUserRatingPK(new UserRatingPK());
        }
        userRating.getUserRatingPK().setUserId(userRating.getUser().getId());
        userRating.getUserRatingPK().setTitleId(userRating.getTitle().getId());

        User user = userRating.getUser();
        if (user != null) {
            user = repository.findUser(user.getId());
            userRating.setUser(user);
        }
        Title title = userRating.getTitle();
        if (title != null) {
            title = repository.findTitle(title.getId());
            userRating.setTitle(title);
        }
        repository.create(userRating);
    }
}
