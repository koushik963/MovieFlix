/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 * @author koushik
 */
@Configuration
@ComponentScan(basePackages = { "com.koushik.movieflix" }, 
        excludeFilters = { @Filter(type = FilterType.ANNOTATION, value = Configuration.class) })
@EnableWebMvc
public class AppConfig {
    
}
