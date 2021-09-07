package com.example.SoftwarePlantRecruitment.service;

import com.example.SoftwarePlantRecruitment.model.Character;
import com.example.SoftwarePlantRecruitment.repository.CharacterRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

@Service
public class CharacterServiceImpl implements CharacterSerivce {

    CharacterRepository characterRepository;

    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public Character getCharacter(long id) {
        Character character;
        try {
            character = characterRepository.getById(id);
        } catch (EntityNotFoundException e) {
            character = new Character();
            getCharacterDetailsFromAPI(character, id);
        }

        return character;
    }



    @Override
    public Character getCharacterDetailsFromAPI(String apiLink) {
        long id = apiLink.charAt(apiLink.length() - 1);
        Character character = new Character();
        try {
            URL url = new URL(apiLink);

            Scanner scanner = new Scanner(url.openStream());
            String apiReadings = "";
            while (scanner.hasNext()) {
                apiReadings += scanner.nextLine();
            }
            JSONObject obj = new JSONObject(apiReadings);
            character.setCharacter_id(id);
            character.setCharacter_name(obj.getString("name"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return character;
    }


    private Character getCharacterDetailsFromAPI(Character character, long id) {
        String urlBuild = "http://localhost:8080/api/people/" + id;
        URL url = null;
        try {
            url = new URL(urlBuild);

            Scanner scanner = new Scanner(url.openStream());
            String apiReadings = new String();
            while (scanner.hasNext()) {
                apiReadings += scanner.nextLine();
            }
            JSONObject obj = new JSONObject(apiReadings);
            character.setCharacter_id(id);
            character.setCharacter_name(obj.getString("name"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return character;
    }
}

