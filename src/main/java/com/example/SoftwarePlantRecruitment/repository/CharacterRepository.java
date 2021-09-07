package com.example.SoftwarePlantRecruitment.repository;

import com.example.SoftwarePlantRecruitment.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character,Long> {

//    @Query("select c from Character c where c.character_name like ?1")
//    List<Character> getCharacterByCharacter_name(String name);
}
