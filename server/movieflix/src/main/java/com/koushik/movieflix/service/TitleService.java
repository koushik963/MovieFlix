/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.service;

import com.koushik.movieflix.entity.Title;
import com.koushik.movieflix.exception.IllegalOrphanException;
import com.koushik.movieflix.exception.TitleAlreadyExistsException;
import com.koushik.movieflix.exception.TitleNotFoundException;
import java.util.List;

public interface TitleService {

    List<Title> retrieveAllTitles() throws TitleNotFoundException;

    Title retrieveTitle(Title title) throws TitleNotFoundException;

    void delete(int id) throws TitleNotFoundException, IllegalOrphanException;

    void update(Title title) throws TitleNotFoundException;
    
     void addTitle(Title title) throws TitleAlreadyExistsException;
}
