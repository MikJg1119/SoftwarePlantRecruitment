package com.example.SoftwarePlantRecruitment.service;

import com.example.SoftwarePlantRecruitment.model.Planet;
import com.example.SoftwarePlantRecruitment.repository.PlanetsRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

@Service
public class PlanetServiceImpl implements PlanetsService{
    PlanetsRepository planetsRepository;

    @Autowired
    public PlanetServiceImpl(PlanetsRepository planetsRepository) {
        this.planetsRepository = planetsRepository;
    }

    @Override
    public Planet getPlanet(long id) {
        Planet planet;
        try {
            planet = planetsRepository.getById(id);
        }catch (EntityNotFoundException e){
            planet = new Planet();

        }
        return planet;
    }

    @Override
    public void addPlanet(Planet planet) {
        planetsRepository.save(planet);
    }

    @Override
    public void deletePlanet(long id) {
        planetsRepository.deleteById(id);
    }

    @Override
    public void deleteAllPlanets() {
        planetsRepository.deleteAll();
    }

    private Planet getPlanetDetailsFromApi(Planet planet, long id){
        return planet;
    }



    @Override
    public Planet getPlanetDetailsFromAPI(String apiLink) {
        long id = apiLink.charAt(apiLink.length() - 1);
        Planet planet = new Planet();
        try {
            URL url = new URL(apiLink);

            Scanner scanner = new Scanner(url.openStream());
            String apiReadings = "";
            while (scanner.hasNext()) {
                apiReadings += scanner.nextLine();
            }
            JSONObject obj = new JSONObject(apiReadings);
            planet.setPlanet_id(id);
            planet.setPlanet_name(obj.getString("name"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return planet;
    }
}
