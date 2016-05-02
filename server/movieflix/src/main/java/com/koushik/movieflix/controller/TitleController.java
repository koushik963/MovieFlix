/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.controller;

import com.koushik.movieflix.entity.Title;
import com.koushik.movieflix.exception.IllegalOrphanException;
import com.koushik.movieflix.exception.TitleAlreadyExistsException;
import com.koushik.movieflix.exception.TitleNotFoundException;
import com.koushik.movieflix.service.TitleService;
import com.koushik.movieflix.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author koushik
 */
@RestController
@RequestMapping(value = "/titles")
public class TitleController {

    @Autowired
    UserService userService;

    @Autowired
    TitleService titleService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Title> listAllTitles() throws TitleNotFoundException {
        List<Title> titles = titleService.retrieveAllTitles();
        return titles;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Title getTitle(@PathVariable("id") int id) throws TitleNotFoundException {
        System.out.println("Fetching Title with id " + id);
        Title title = new Title(id);
        return titleService.retrieveTitle(title);
    }

    @RequestMapping(value = "/admin/delete/{titleId}", method = RequestMethod.DELETE)
    public void deleteTitle(@PathVariable("titleId") int id) throws TitleNotFoundException, IllegalOrphanException {
        System.out.println("Deleting Title with id " + id);
        titleService.delete(id);
    }

    @RequestMapping(value = "/admin/update/{id}", method = RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public Title updateUser(@PathVariable("id") int id, @RequestBody Title title) throws TitleNotFoundException {
        System.out.println("Updating title " + id);

        Title titleToBeUpdated = titleService.retrieveTitle(title);

        // updating title with new data
        titleToBeUpdated.setActors(title.getActors());
        titleToBeUpdated.setAwards(title.getAwards());
        titleToBeUpdated.setCountry(title.getCountry());
        titleToBeUpdated.setDirector(title.getDirector());
        titleToBeUpdated.setGenre(title.getGenre());
        titleToBeUpdated.setImdbId(title.getImdbId());
        titleToBeUpdated.setImdbrating(title.getImdbrating());
        titleToBeUpdated.setImdbvotes(title.getImdbvotes());
        titleToBeUpdated.setLanguage(title.getLanguage());
        titleToBeUpdated.setPlot(title.getPlot());
        titleToBeUpdated.setPoster(title.getPoster());
        titleToBeUpdated.setRated(title.getRated());
        titleToBeUpdated.setReleased(title.getReleased());
        titleToBeUpdated.setRuntime(title.getRuntime());
        titleToBeUpdated.setTitle(title.getTitle());
        titleToBeUpdated.setType(title.getType());
        titleToBeUpdated.setWriter(title.getWriter());
        titleToBeUpdated.setYear(title.getYear());

        titleService.update(titleToBeUpdated);
        return titleToBeUpdated;
    }

    @RequestMapping(value = "/admin/create", method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
    public void createTitle(@RequestBody Title title, UriComponentsBuilder ucBuilder) throws TitleAlreadyExistsException {
        System.out.println("Creating titles " + title.getTitle());
        titleService.addTitle(title);
    }
}
