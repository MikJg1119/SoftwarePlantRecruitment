package com.example.SoftwarePlantRecruitment.service;

import com.example.SoftwarePlantRecruitment.model.Film;
import com.example.SoftwarePlantRecruitment.model.Report;
import com.example.SoftwarePlantRecruitment.repository.ReportsRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class ReportsServiceImpl implements ReportsService{

    ReportsRepository repository;
    FilmsService filmsService;
    CharacterSerivce characterSerivce;

    @Autowired
    public ReportsServiceImpl(ReportsRepository repository, FilmsService filmsService, CharacterSerivce characterSerivce) {
        this.repository = repository;
        this.filmsService = filmsService;
        this.characterSerivce = characterSerivce;
    }

    @Override
    public List<Report> allReports() {
        return repository.findAll();
    }

    @Override
    public Report getReport(long id) {
        return  repository.getById(id);

    }

    @Override
    public boolean deleteAllReports() {
       repository.deleteAll();
       if (repository.findAll()==null){
           return true;
       }
       return false;
    }

    @Override
    public boolean deleteReport(long id) {
        repository.deleteById(id);
        if (repository.getById(id)==null){
            return true;
        }
        return false;
    }

    @Override
    public boolean addReport(Report report) {
        repository.save(report);
        if (repository.getById(report.getReport_id())!=null){
            return true;
        }
        return false;
    }


    @Override
    public List<Film> filmsMatchingQueries(String character_phrase, String planet_phrase, long report_id){
        String characterSearchLink = "http://localhost:8080/api/people/?search="+character_phrase;
        String planetSearchLink = "http://localhost:8080/api/planets/?search="+planet_phrase;
        List<Film> filmsMatchingCharacterPhrase = new ArrayList<Film>();
        List<Film> filmsMatchingPlanetPhrase = new ArrayList<Film>();
        List<Film> filmsMatchingBothPhrases = new ArrayList<Film>();
        try {
            URL characterUrl = new URL(characterSearchLink);
            URL planetUrl = new URL(planetSearchLink);
            Scanner scanner = new Scanner(characterUrl.openStream());
            String apiReadings = new String();
            while (scanner.hasNext()) {
                apiReadings += scanner.nextLine();
            }
            JSONObject obj = new JSONObject(apiReadings);
            JSONArray resultsArray = obj.getJSONArray("results");
            for (int i=0; i<resultsArray.length();i++){
                JSONObject characterObject = resultsArray.getJSONObject(i);
                JSONArray filmsStartingCharacterArray = characterObject.getJSONArray("films");
                for (int j=0; j<filmsStartingCharacterArray.length();j++) {
                    filmsMatchingCharacterPhrase.add(filmsService.getFilmDetailsFromApi(filmsStartingCharacterArray.getString(j)));
                }
            }
            scanner = new Scanner(planetUrl.openStream());
            apiReadings = new String();
            while (scanner.hasNext()) {
                apiReadings += scanner.nextLine();
            }
            obj = new JSONObject(apiReadings);
            resultsArray = obj.getJSONArray("results");
            for (int i=0; i<resultsArray.length();i++){
                JSONObject characterObject = resultsArray.getJSONObject(i);
                JSONArray filmsStartingCharacterArray = characterObject.getJSONArray("films");
                for (int j=0; j<filmsStartingCharacterArray.length();j++) {
                    filmsMatchingPlanetPhrase.add(filmsService.getFilmDetailsFromApi(filmsStartingCharacterArray.getString(j)));
                }
            }
        }catch (MalformedURLException urlException){
            System.out.println("Film not found in database and exception connecting to SWApi"+urlException.getMessage());
        }catch (IOException ioException) {
            System.out.println("Film not found in database and exception reading from SWApi" + ioException.getMessage());
        }
        for (Film film : filmsMatchingCharacterPhrase){
            for(Film planetFilm : filmsMatchingPlanetPhrase){
                if (film.equals(planetFilm)){
                    filmsMatchingBothPhrases.add(film);
                }
            }
        }
        repository.getById(report_id).setFilmsMatchingQueries(filmsMatchingBothPhrases);
        return filmsMatchingBothPhrases;
    }
}
