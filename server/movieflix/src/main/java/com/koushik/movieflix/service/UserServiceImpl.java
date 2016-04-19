/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.service;

import com.koushik.movieflix.entity.User;
import com.koushik.movieflix.exception.UserAlreadyExistsException;
import com.koushik.movieflix.exception.UserNotFoundException;
import com.koushik.movieflix.repositry.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author koushik
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findOne(String id) throws UserNotFoundException {
        User user = repository.findOne(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User create(User user) throws UserAlreadyExistsException {
        User existing = repository.findByEmail(user.getEmail());
        if (existing != null) {
            throw new UserAlreadyExistsException();
        }
        return repository.create(user);
    }

    @Override
    public User update(String id, User user) throws UserNotFoundException {
        User existing = repository.findOne(id);
        if (existing == null) {
            throw new UserNotFoundException();
        }
        return repository.update(user);
    }

    @Override
    public void delete(String id) throws UserNotFoundException {
        User existing = repository.findOne(id);
        if (existing == null) {
            throw new UserNotFoundException();
        }
        repository.delete(existing);
    }

}
