/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.janhric.balancergameserver;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Honza
 */
@Entity
@Table(name = "scores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Scores.findAll", query = "SELECT s FROM Scores s")
    , @NamedQuery(name = "Scores.findById", query = "SELECT s FROM Scores s WHERE s.id = :id")
    , @NamedQuery(name = "Scores.findByName", query = "SELECT s FROM Scores s WHERE s.name = :name")
    , @NamedQuery(name = "Scores.findByScore", query = "SELECT s FROM Scores s WHERE s.score = :score")
    , @NamedQuery(name = "Scores.findByDate", query = "SELECT s FROM Scores s WHERE s.date = :date")})
public class Scores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "score")
    private int score;
    @Size(max = 20)
    @Column(name = "date")
    private String date;
    @Column(name = "latitude")
    private float latitude;
    @Column(name = "longitude")
    private float longitude;

    public Scores() {
    }

    public Scores(Integer id) {
        this.id = id;
    }

    public Scores(Integer id, int score) {
        this.id = id;
        this.score = score;
    }
    
    public Scores(Integer id, int score, String name, String date, float latitude, float longitude) {
        this.id = id;
        this.score = score;
        this.name = name;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public void setLatitude(float latitude){
        this.latitude = latitude;
    }
    
    public float getLatitude(){
        return this.latitude;
    }
    
    public void setLongitude(float longitude){
        this.longitude = longitude;
    }
    
    public float getLongitude(){
        return this.longitude;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Scores)) {
            return false;
        }
        Scores other = (Scores) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.janhric.restservertest.Scores[ id=" + id + 
                ", name: " + name + 
                ", score: " + score + 
                ", date: " + date + 
                "coordinates: " + latitude + ", " + longitude + "]";
    }
    
}
