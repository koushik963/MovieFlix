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

/**
 *
 * @author Koushik
 */
@Embeddable
public class UserRatingPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "user_id")
    private int userId;
    @Basic(optional = false)
    @Column(name = "title_id")
    private int titleId;

    public UserRatingPK(){
    
    }

    public UserRatingPK(int userId, int titleId) {
        this.userId = userId;
        this.titleId = titleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.userId;
        hash = 29 * hash + this.titleId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserRatingPK other = (UserRatingPK) obj;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.titleId != other.titleId) {
            return false;
        }
        return true;
    }

}
