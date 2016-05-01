/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Koushik
 */
@Entity
@Table(name = "user_rating")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserRating.findAll", query = "SELECT u FROM UserRating u"),
    @NamedQuery(name = "UserRating.findByUserId", query = "SELECT u FROM UserRating u WHERE u.userRatingPK.userId = :userId"),
    @NamedQuery(name = "UserRating.findByTitleId", query = "SELECT u FROM UserRating u WHERE u.userRatingPK.titleId = :titleId"),
    @NamedQuery(name = "UserRating.findByRating", query = "SELECT u FROM UserRating u WHERE u.rating = :rating"),
    @NamedQuery(name = "UserRating.findByComment", query = "SELECT u FROM UserRating u WHERE u.comment = :comment")})
public class UserRating implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserRatingPK userRatingPK;
    @Column(name = "rating")
    private Short rating;
    @Column(name = "comment")
    private String comment;
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "title_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Title title;
    
    public UserRating(){
    
    }

    public UserRating(UserRatingPK userRatingPK) {
        this.userRatingPK = userRatingPK;
    }

    public UserRating(int userId, int titleId) {
        this.userRatingPK = new UserRatingPK(userId, titleId);
    }
    
    
    @JsonIgnore
    public User getUser() {
        return user;
    }

    @JsonIgnore
    public Title getTitle() {
        return title;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    
    public UserRatingPK getUserRatingPK() {
        return userRatingPK;
    }

    public void setUserRatingPK(UserRatingPK userRatingPK) {
        this.userRatingPK = userRatingPK;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.userRatingPK);
        hash = 79 * hash + Objects.hashCode(this.rating);
        hash = 79 * hash + Objects.hashCode(this.comment);
        hash = 79 * hash + Objects.hashCode(this.user);
        hash = 79 * hash + Objects.hashCode(this.title);
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
        final UserRating other = (UserRating) obj;
        if (!Objects.equals(this.comment, other.comment)) {
            return false;
        }
        if (!Objects.equals(this.userRatingPK, other.userRatingPK)) {
            return false;
        }
        if (!Objects.equals(this.rating, other.rating)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return true;
    }
    
}
