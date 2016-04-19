/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.entity;

import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author koushik
 */

/*

CREATE TABLE `user` (
	`id` bigint AUTO_INCREMENT,
	`firstname` TEXT NOT NULL,
	`lastname` TEXT NOT NULL,
	`email` VARCHAR(200) NOT NULL UNIQUE,
	`password` TEXT NOT NULL,
	`country` TEXT NOT NULL,
	`role_id` bigint NOT NULL,
	PRIMARY KEY (`id`)
);

*/

@Entity
@Data
@Table
@NamedQueries (
		@NamedQuery(name="User.findByEmail", query="SELECT u FROM User u WHERE u.email= :pEmail")
)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    
    @Column(unique=true)
    @NotNull
    private String email;
    private String password;
    private String country;
    
//    @JoinColumn(name = "role_id")
//    @OneToOne
//    private int roleid;
}
