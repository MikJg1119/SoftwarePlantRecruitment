package com.example.SoftwarePlantRecruitment.service;

import com.example.SoftwarePlantRecruitment.model.Character;

public interface CharacterSerivce {
    Character getCharacter(long id);
    Character getCharacterDetailsFromAPI(String apiLink);

}
