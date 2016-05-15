/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.repositry;

import com.koushik.movieflix.entity.User;

/**
 *
 * @author koushik
 */
public interface UserRepository {

    public User findUser(Integer id);

    public User findByEmail(String email);

    public void create(User user);
    
    public void edit(User user);
}
