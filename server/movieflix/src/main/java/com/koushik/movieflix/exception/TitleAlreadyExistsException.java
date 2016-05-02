/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author koushik
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Title Already exists")
public class TitleAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 45465465465465465L;
}
