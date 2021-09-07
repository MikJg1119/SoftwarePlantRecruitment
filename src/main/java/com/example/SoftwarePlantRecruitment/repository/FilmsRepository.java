package com.example.SoftwarePlantRecruitment.repository;

import com.example.SoftwarePlantRecruitment.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmsRepository extends JpaRepository<Film, Long> {


}
