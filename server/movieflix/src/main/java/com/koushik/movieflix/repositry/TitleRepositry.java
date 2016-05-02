/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.repositry;

import com.koushik.movieflix.entity.Title;
import java.util.List;

/**
 *
 * @author koushik
 */
public interface TitleRepositry {

    public void create(Title title);

    public void edit(Title title);

    public void destroy(Title title) ;

    public List<Title> findTitleEntities();

    public List<Title> findTitleEntities(boolean all, int maxResults, int firstResult);

    public Title findTitle(Integer id);

}
