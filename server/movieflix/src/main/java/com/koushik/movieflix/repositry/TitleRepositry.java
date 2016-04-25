/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.repositry;

import com.koushik.movieflix.entity.Title;
import com.koushik.movieflix.exception.IllegalOrphanException;
import com.koushik.movieflix.exception.NonexistentEntityException;
import java.util.List;

/**
 *
 * @author koushik
 */
public interface TitleRepositry {

    public void create(Title title);

    public void edit(Title title);

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException;

    public List<Title> findTitleEntities();

    public List<Title> findTitleEntities(int maxResults, int firstResult);

    public List<Title> findTitleEntities(boolean all, int maxResults, int firstResult);

    public Title findTitle(Integer id);

}
