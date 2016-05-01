package com.koushik.movieflix.controller;

import com.koushik.movieflix.entity.Title;
import com.koushik.movieflix.service.TitleService;
import com.koushik.movieflix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class AdminRestController {

    @Autowired
    UserService userService;

    @Autowired
    TitleService titleService;

    
}
