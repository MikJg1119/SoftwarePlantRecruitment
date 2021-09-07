package com.example.SoftwarePlantRecruitment.service;

import com.example.SoftwarePlantRecruitment.model.Film;
import com.example.SoftwarePlantRecruitment.model.Report;

import java.util.List;

public interface ReportsService {
    List<Report> allReports();
    Report getReport(long id);
    boolean deleteAllReports();
    boolean deleteReport(long id);
    boolean addReport(Report report);
    List<Film> filmsMatchingQueries(String Character_phrase, String Planet_phrase,long report_id);
}
