package com.example.SoftwarePlantRecruitment.repository;

import com.example.SoftwarePlantRecruitment.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character,Long> {

}
