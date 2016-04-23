/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.koushik.movieflix.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Koushik
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public UserRating(UserRatingPK userRatingPK) {
        this.userRatingPK = userRatingPK;
    }

    public UserRating(int userId, int titleId) {
        this.userRatingPK = new UserRatingPK(userId, titleId);
    }
    
}
