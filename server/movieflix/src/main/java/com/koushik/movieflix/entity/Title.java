/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.koushik.movieflix.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import lombok.Data;

/**
 *
 * @author Koushik
 */
@Entity
@Data
@Table(name = "title")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Title.findAll", query = "SELECT t FROM Title t"),
    @NamedQuery(name = "Title.findById", query = "SELECT t FROM Title t WHERE t.id = :id"),
    @NamedQuery(name = "Title.findByCountry", query = "SELECT t FROM Title t WHERE t.country = :country"),
    @NamedQuery(name = "Title.findByAwards", query = "SELECT t FROM Title t WHERE t.awards = :awards"),
    @NamedQuery(name = "Title.findByPoster", query = "SELECT t FROM Title t WHERE t.poster = :poster"),
    @NamedQuery(name = "Title.findByImdbvotes", query = "SELECT t FROM Title t WHERE t.imdbvotes = :imdbvotes"),
    @NamedQuery(name = "Title.findByImdbrating", query = "SELECT t FROM Title t WHERE t.imdbrating = :imdbrating"),
    @NamedQuery(name = "Title.findByImdbId", query = "SELECT t FROM Title t WHERE t.imdbId = :imdbId"),
    @NamedQuery(name = "Title.findByType", query = "SELECT t FROM Title t WHERE t.type = :type"),
    @NamedQuery(name = "Title.findByYear", query = "SELECT t FROM Title t WHERE t.year = :year"),
    @NamedQuery(name = "Title.findByReleased", query = "SELECT t FROM Title t WHERE t.released = :released"),
    @NamedQuery(name = "Title.findByRuntime", query = "SELECT t FROM Title t WHERE t.runtime = :runtime"),
    @NamedQuery(name = "Title.findByGenre", query = "SELECT t FROM Title t WHERE t.genre = :genre"),
    @NamedQuery(name = "Title.findByDirector", query = "SELECT t FROM Title t WHERE t.director = :director"),
    @NamedQuery(name = "Title.findByWriter", query = "SELECT t FROM Title t WHERE t.writer = :writer"),
    @NamedQuery(name = "Title.findByActors", query = "SELECT t FROM Title t WHERE t.actors = :actors"),
    @NamedQuery(name = "Title.findByPlot", query = "SELECT t FROM Title t WHERE t.plot = :plot"),
    @NamedQuery(name = "Title.findByLanguage", query = "SELECT t FROM Title t WHERE t.language = :language"),
    @NamedQuery(name = "Title.findByTitle", query = "SELECT t FROM Title t WHERE t.title = :title"),
    @NamedQuery(name = "Title.findByRated", query = "SELECT t FROM Title t WHERE t.rated = :rated")})
public class Title implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "country")
    private String country;
    @Basic(optional = false)
    @Column(name = "awards")
    private String awards;
    @Column(name = "poster")
    private String poster;
    @Basic(optional = false)
    @Column(name = "imdbvotes")
    private long imdbvotes;
    @Column(name = "imdbrating")
    private Float imdbrating;
    @Column(name = "imdbId")
    private String imdbId;
    @Column(name = "Type")
    private String type;
    @Column(name = "year")
    @Temporal(TemporalType.DATE)
    private Date year;
    @Column(name = "released")
    @Temporal(TemporalType.DATE)
    private Date released;
    @Column(name = "runtime")
    private Integer runtime;
    @Column(name = "genre")
    private String genre;
    @Column(name = "director")
    private String director;
    @Column(name = "writer")
    private String writer;
    @Column(name = "actors")
    private String actors;
    @Column(name = "plot")
    private String plot;
    @Column(name = "language")
    private String language;
    @Column(name = "title")
    private String title;
    @Column(name = "rated")
    private String rated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "title")
    private Collection<UserRating> userRatingCollection;

    public Title() {
    }

    public Title(Integer id) {
        this.id = id;
    }

    public Title(Integer id, String country, String awards, long imdbvotes) {
        this.id = id;
        this.country = country;
        this.awards = awards;
        this.imdbvotes = imdbvotes;
    }
  
}
