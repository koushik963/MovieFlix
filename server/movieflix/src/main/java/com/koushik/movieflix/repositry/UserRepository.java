/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.repositry;

import com.koushik.movieflix.entity.User;
import com.koushik.movieflix.exception.IllegalOrphanException;
import com.koushik.movieflix.exception.NonexistentEntityException;
import java.util.List;

/**
 *
 * @author koushik
 */
public interface UserRepository {

    public List<User> findUserEntities(boolean all, int maxResults, int firstResult);

    public List<User> findUserEntities(int maxResults, int firstResult);

    public List<User> findUserEntities();

    public User findUser(Integer id);

    public User findByEmail(String email);

    public void create(User user);

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception;

    public void destroy(Integer id)  throws IllegalOrphanException, NonexistentEntityException;
}
