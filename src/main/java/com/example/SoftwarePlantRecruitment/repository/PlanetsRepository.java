package com.example.SoftwarePlantRecruitment.repository;

import com.example.SoftwarePlantRecruitment.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetsRepository extends JpaRepository<Planet,Long> {

}
