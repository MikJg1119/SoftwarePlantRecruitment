package com.example.SoftwarePlantRecruitment.repository;

import com.example.SoftwarePlantRecruitment.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmsRepository extends JpaRepository<Film, Long> {

//    @Query("select f from Film f where f.cast.character_name like ?1")
//    List<Film> searchByCast(String name);
//
//    @Query("select f from Film f where f.planets.planet_name like ?1")
//    List<Film> searchByPlanets(String name);
}
