package com.example.SoftwarePlantRecruitment.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long film_id;

    String film_name;
    @ManyToMany(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinTable( name = "people_films" ,
                joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"),
                inverseJoinColumns = @JoinColumn(name = "character_id", referencedColumnName = "character_id"))
    List<Character> cast;

    @ManyToMany(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinTable( name = "planets_films" ,
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "planet_id", referencedColumnName = "planet_id"))
    List<Planet> planets;




}
