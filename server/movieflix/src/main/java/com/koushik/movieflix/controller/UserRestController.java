/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.controller;

import com.koushik.movieflix.entity.Title;
import com.koushik.movieflix.entity.User;
import com.koushik.movieflix.entity.UserRating;
import com.koushik.movieflix.exception.UserAlreadyExistsException;
import com.koushik.movieflix.service.TitleService;
import com.koushik.movieflix.service.UserService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author koushik
 */

@RestController
@EnableWebMvc
public class UserRestController {
    @Autowired
    UserService userService;

    @Autowired
    TitleService titleServiceImpl;

    @RequestMapping(value = "/user/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("email") String email) {
        System.out.println("Fetching User with id " + email);
        User user = new User();
        user.setEmail(email);
        User user1 = userService.login(user);
        if (user1 == null) {
            System.out.println("User with id " + email + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user1, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getEmail());

        boolean userExist = userService.isUserExist(user);
        if (userExist) {
            System.out.println("A User with name " + user.getEmail() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        try {
            user.setEnables(true);
            userService.signup(user);
        } catch (UserAlreadyExistsException ex) {
            Logger.getLogger(UserRestController.class.getName()).log(Level.SEVERE, null, ex);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/title", method = RequestMethod.GET)
    public ResponseEntity<List<Title>> listAllTitles() {
        List<Title> titles = titleServiceImpl.retrieveAllTitles();
        if (titles.isEmpty()) {
            return new ResponseEntity<List<Title>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Title>>(titles, HttpStatus.OK);
    }

    @RequestMapping(value = "/title/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Title> getTitle(@PathVariable("id") int id) {
        System.out.println("Fetching Title with id " + id);
        Title title = new Title(id);
        Title retrievedTitle = titleServiceImpl.retrieveTitle(title);
        if (retrievedTitle == null) {
            System.out.println("Title with id " + id + " not found");
            return new ResponseEntity<Title>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Title>(retrievedTitle, HttpStatus.OK);
    }

    @RequestMapping(value = "/rating/{user}/{title}/{rate}/{comment}", method = RequestMethod.POST)
    public ResponseEntity<Void> rateAndComment(@PathVariable("rate") short rate, @PathVariable("user") int userId, @PathVariable("comment") String comment, @PathVariable("title") int titleId, UriComponentsBuilder ucBuilder) {

        System.out.println("user with id: " + userId + " has just rated title with id: " + titleId + " with rate: " + rate + " and commented: " + comment);
        UserRating rating = new UserRating();
        rating.setUser(new User(userId));
        rating.setTitle(new Title(titleId));
        rating.setComment(comment);
        rating.setRating(rate);
        userService.rate(rating);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/logout}", method = RequestMethod.GET)
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login";
    }
}
