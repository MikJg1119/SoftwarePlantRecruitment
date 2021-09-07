package com.example.SoftwarePlantRecruitment.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long report_id;

    String query_criteria_character_phrase;
    String query_criteria_planet_phrase;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable( name = "matching_films" ,
            joinColumns = @JoinColumn(name = "report_id", referencedColumnName = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"))
    List<Film> filmsMatchingQueries;
}
