/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.controller;

import com.koushik.movieflix.entity.Title;
import com.koushik.movieflix.entity.User;
import com.koushik.movieflix.entity.UserRating;
import com.koushik.movieflix.service.UserRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author koushik
 */
@RestController
public class RatingController {

    @Autowired
    UserRatingService userService;

    @RequestMapping(value = "/rating/{user}/{title}/{rate}/{comment}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void rateAndComment(@PathVariable("rate") short rate, @PathVariable("user") int userId, @PathVariable("comment") String comment, @PathVariable("title") int titleId, UriComponentsBuilder ucBuilder) {

        System.out.println("user with id: " + userId + " has just rated title with id: " + titleId + " with rate: " + rate + " and commented: " + comment);
        UserRating rating = new UserRating();
        rating.setUser(new User(userId));
        rating.setTitle(new Title(titleId));
        rating.setComment(comment);
        rating.setRating(rate);
        userService.rate(rating);
    }
}
