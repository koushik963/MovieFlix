/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.repositry;

import com.koushik.movieflix.entity.User;
import java.util.List;

/**
 *
 * @author koushik
 */
public interface UserRepository {

    public List<User> findAll();

    public User findOne(String id);

    public User findByEmail(String email);

    public User create(User user);

    public User update(User user);

    public void delete(User user);
}
