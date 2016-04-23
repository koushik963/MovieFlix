/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.koushik.movieflix.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Koushik
 */
@Embeddable
@Data
@NoArgsConstructor
public class UserRatingPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "user_id")
    private int userId;
    @Basic(optional = false)
    @Column(name = "title_id")
    private int titleId;


    public UserRatingPK(int userId, int titleId) {
        this.userId = userId;
        this.titleId = titleId;
    }
    
}
