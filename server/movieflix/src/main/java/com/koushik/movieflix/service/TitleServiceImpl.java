/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.service;

import com.koushik.movieflix.entity.Title;
import com.koushik.movieflix.exception.IllegalOrphanException;
import com.koushik.movieflix.exception.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.koushik.movieflix.repositry.TitleRepositry;
import com.koushik.movieflix.repositry.UserRatingRepositry;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TitleServiceImpl implements TitleService {

    @Autowired
    TitleRepositry titleRepositry;
           
    @Override
    public List<Title> retrieveAllTitles() {
        return titleRepositry.findTitleEntities();
    }

    @Override
    public Title retrieveTitle(Title title) {
        return titleRepositry.findTitle(title.getId());
    }

    @Override
    public void delete(int id) {
        try {
            Title findTitle = titleRepositry.findTitle(id);
            if (findTitle == null) {
                System.out.println("Unable to delete. title with id " + id + " not found");
            }
            titleRepositry.destroy(id);
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            Logger.getLogger(TitleServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Title title) {
        titleRepositry.edit(title);
    }

    @Override
    public void addTitle(Title title) {
        titleRepositry.create(title);
    }
}
