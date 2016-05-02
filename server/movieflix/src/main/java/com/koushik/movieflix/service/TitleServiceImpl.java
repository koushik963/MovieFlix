/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.service;

import com.koushik.movieflix.entity.Title;
import com.koushik.movieflix.entity.UserRating;
import com.koushik.movieflix.exception.IllegalOrphanException;
import com.koushik.movieflix.exception.TitleAlreadyExistsException;
import com.koushik.movieflix.exception.TitleNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.koushik.movieflix.repositry.TitleRepositry;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TitleServiceImpl implements TitleService {

    @Autowired
    TitleRepositry titleRepositry;

    @Override
    public List<Title> retrieveAllTitles() throws TitleNotFoundException {
        List<Title> persistedTitles = titleRepositry.findTitleEntities();
        if (persistedTitles.isEmpty()) {
            throw new TitleNotFoundException();
        }
        return persistedTitles;
    }

    @Override
    public Title retrieveTitle(Title title) throws TitleNotFoundException {
        Title persistedTitle = titleRepositry.findTitle(title.getId());
        if (persistedTitle == null) {
            throw new TitleNotFoundException();
        }
        return persistedTitle;
    }
    
    @Override
    public void delete(int id) throws TitleNotFoundException, IllegalOrphanException {

        Title findTitle = titleRepositry.findTitle(id);
        if (findTitle == null) {
            System.out.println("Unable to delete. title with id " + id + " not found");
            throw new TitleNotFoundException();
        }
        List<String> illegalOrphanMessages = null;
        Collection<UserRating> userRatingCollectionOrphanCheck = findTitle.getUserRatingCollection();
        for (UserRating userRatingCollectionOrphanCheckUserRating : userRatingCollectionOrphanCheck) {
            if (illegalOrphanMessages == null) {
                illegalOrphanMessages = new ArrayList<>();
            }
            illegalOrphanMessages.add("This Title (" + findTitle + ") cannot be destroyed since the UserRating " + userRatingCollectionOrphanCheckUserRating + " in its userRatingCollection field has a non-nullable title field.");
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        titleRepositry.destroy(findTitle);
    }

    public void checkTitleExistence(Title title) throws TitleAlreadyExistsException{
    
        Title findTitle = titleRepositry.findTitle(title.getId());
        if (findTitle != null) {
            System.out.println("Unable to create. title with id " + title.getId() + " already exists");
            throw new TitleAlreadyExistsException();
        }
    }
    @Override
    public void update(Title title) throws TitleNotFoundException {
        Title persistentTitle = titleRepositry.findTitle(title.getId());
        if (persistentTitle == null) {
            System.out.println("Unable to delete. title with id " + title.getId() + " not found");
            throw new TitleNotFoundException();
        }
        Collection<UserRating> userRatingCollection = persistentTitle.getUserRatingCollection();
        title.setUserRatingCollection(userRatingCollection);
        titleRepositry.edit(title);
    }

    @Override
    public void addTitle(Title title) throws TitleAlreadyExistsException {
        checkTitleExistence(title);
        titleRepositry.create(title);
    }
}
