package com.example.SoftwarePlantRecruitment.service;

import com.example.SoftwarePlantRecruitment.model.Planet;

public interface PlanetsService {
    Planet getPlanet(long id);
    void addPlanet(Planet planet);
    void deletePlanet(long id);
    void deleteAllPlanets();
    Planet getPlanetDetailsFromAPI(String apiLink);
}
