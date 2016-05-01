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
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

/**
 *
 * @author Koushik
 */
@Entity
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
    private Integer id;
    @Basic(optional = false)
    private String country;
    @Basic(optional = false)
    private String awards;
    private String poster;
    @Basic(optional = false)
    private long imdbvotes;
    private Float imdbrating;
    private String imdbId;
    private String type;
    private int year;
    @Temporal(TemporalType.DATE)
    private Date released;
    private Integer runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String language;
    private String title;
    private String rated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "title", fetch = FetchType.EAGER)
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public long getImdbvotes() {
        return imdbvotes;
    }

    public void setImdbvotes(long imdbvotes) {
        this.imdbvotes = imdbvotes;
    }

    public Float getImdbrating() {
        return imdbrating;
    }

    public void setImdbrating(Float imdbrating) {
        this.imdbrating = imdbrating;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getReleased() {
        return released;
    }

    public void setReleased(Date released) {
        this.released = released;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public Collection<UserRating> getUserRatingCollection() {
        return userRatingCollection;
    }

    public void setUserRatingCollection(Collection<UserRating> userRatingCollection) {
        this.userRatingCollection = userRatingCollection;
    }

}
