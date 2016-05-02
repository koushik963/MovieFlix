/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.controller;

import com.koushik.movieflix.entity.User;
import com.koushik.movieflix.exception.UserAlreadyExistsException;
import com.koushik.movieflix.exception.UserNotFoundException;
import com.koushik.movieflix.service.TitleService;
import com.koushik.movieflix.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author koushik
 */
@RestController
@RequestMapping(value = "/users")
public class UserRestController {

    @Autowired
    UserService userService;

    @Autowired
    TitleService titleService;

    @RequestMapping(value = "/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable("email") String email) throws UserNotFoundException {
        System.out.println("Fetching User with id " + email);
        User user = new User();
        user.setEmail(email);
        return userService.login(user);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) throws UserAlreadyExistsException {
        System.out.println("Creating User " + user.getEmail());
        user.setEnables(true);
        userService.signup(user);
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
