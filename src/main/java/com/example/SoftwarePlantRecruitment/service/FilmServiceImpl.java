package com.example.SoftwarePlantRecruitment.service;

import com.example.SoftwarePlantRecruitment.model.Character;
import com.example.SoftwarePlantRecruitment.model.Film;
import com.example.SoftwarePlantRecruitment.model.Planet;
import com.example.SoftwarePlantRecruitment.repository.FilmsRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

@Service
public class FilmServiceImpl implements FilmsService{

    FilmsRepository filmsRepository;
    CharacterSerivce characterSerivce;
    PlanetsService planetService;

    @Autowired
    public FilmServiceImpl(FilmsRepository filmsRepository, CharacterSerivce characterSerivce, PlanetsService planetService) {
        this.filmsRepository=filmsRepository;
        this.characterSerivce=characterSerivce;
        this.planetService = planetService;
    }

    @Override
    public Film getFilm(long id) {
        Film film;
        try {
            film = filmsRepository.getById(id);
        }catch (EntityNotFoundException e){
            film = new Film();
            getFilmDetailsFromApi(film,id);
        }
        return null;
    }

    @Override
    public void addFilm(Film film) {
        filmsRepository.save(film);
    }

    @Override
    public void deleteFilm(long id) {
        filmsRepository.deleteById(id);
    }

    @Override
    public void deleteAllFilms() {
        filmsRepository.deleteAll();
    }


    private Film getFilmDetailsFromApi(Film film, long id){
        String urlBuild = "http://localhost:8080/api/films/"+id;

        try {
            URL url = new URL(urlBuild);
            Scanner scanner = new Scanner(url.openStream());
            String apiReadings = new String();
            while (scanner.hasNext()) {
                apiReadings += scanner.nextLine();
            }
            JSONObject obj = new JSONObject(apiReadings);

            film.setFilm_id(id);
            film.setFilm_name(obj.getString("title"));
            JSONArray charactersArray = obj.getJSONArray("characters");
            for(int i=0; i<charactersArray.length();i++){
                Character character = characterSerivce.getCharacterDetailsFromAPI(charactersArray.getString(i));
                film.getCast().add(character);
            }
            JSONArray planetsArray = obj.getJSONArray("planets");
            for(int i=0; i<planetsArray.length();i++){
                Planet planet = planetService.getPlanetDetailsFromAPI(planetsArray.getString(i));
                film.getPlanets().add(planet);
            }

        }catch (MalformedURLException urlException){
            System.out.println("Film not found in database and exception connecting to SWApi"+urlException.getMessage());
        }catch (IOException ioException){
            System.out.println("Film not found in database and exception reading from SWApi"+ioException.getMessage());
        }
        return film;
    }
    @Override
    public Film getFilmDetailsFromApi(String apiLink){
        Film film = new Film();
        try {
            URL url = new URL(apiLink);
            Scanner scanner = new Scanner(url.openStream());
            String apiReadings = new String();
            while (scanner.hasNext()) {
                apiReadings += scanner.nextLine();
            }
            JSONObject obj = new JSONObject(apiReadings);

            film.setFilm_id(apiLink.charAt(apiLink.length()-1));
            film.setFilm_name(obj.getString("title"));
            JSONArray charactersArray = obj.getJSONArray("characters");
            for(int i=0; i<charactersArray.length();i++){
                Character character = characterSerivce.getCharacterDetailsFromAPI(charactersArray.getString(i));
                film.getCast().add(character);
            }
            JSONArray planetsArray = obj.getJSONArray("planets");
            for(int i=0; i<planetsArray.length();i++){
                Planet planet = planetService.getPlanetDetailsFromAPI(planetsArray.getString(i));
                film.getPlanets().add(planet);
            }

        }catch (MalformedURLException urlException){
            System.out.println("Film not found in database and exception connecting to SWApi"+urlException.getMessage());
        }catch (IOException ioException){
            System.out.println("Film not found in database and exception reading from SWApi"+ioException.getMessage());
        }
        return film;
    }

}
