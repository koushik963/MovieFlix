/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.service;

import com.koushik.movieflix.entity.User;
import com.koushik.movieflix.exception.UserAlreadyExistsException;
import com.koushik.movieflix.exception.UserNotFoundException;
import java.util.List;

/**
 *
 * @author koushik
 */
public interface UserService {

    public List<User> findAll();

    public User findOne(String id) throws UserNotFoundException;

    public User create(User user) throws UserAlreadyExistsException;

    public User update(String id, User user) throws UserNotFoundException;

    public void delete(String id) throws UserNotFoundException;
}
