/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.service;

import com.koushik.movieflix.entity.Title;
import java.util.List;

public interface TitleService {

    List<Title> retrieveAllTitles();

    Title retrieveTitle(Title title);

    void delete(Title title);

    void update(Title title);
    
     void addTitle(Title title);
}
