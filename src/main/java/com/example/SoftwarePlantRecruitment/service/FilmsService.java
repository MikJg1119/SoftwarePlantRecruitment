package com.example.SoftwarePlantRecruitment.service;

import com.example.SoftwarePlantRecruitment.model.Film;

public interface FilmsService {
    Film getFilm(long id);
    void addFilm(Film film);
    void deleteFilm(long id);
    void deleteAllFilms();
//    List<Film> queryFilmByPlanetName(String planet);
//    List<Film> queryFilmByCharacterName(String planet);
    Film getFilmDetailsFromApi(String apiLink);
}
