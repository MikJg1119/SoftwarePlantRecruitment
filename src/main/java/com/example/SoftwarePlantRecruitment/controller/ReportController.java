package com.example.SoftwarePlantRecruitment.controller;

import com.example.SoftwarePlantRecruitment.model.QueryCriteria;
import com.example.SoftwarePlantRecruitment.model.Report;
import com.example.SoftwarePlantRecruitment.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReportController {

    ReportsService reportsService;

    @Autowired
    public ReportController(ReportsService reportsService) {
        this.reportsService = reportsService;

    }

    @PutMapping("/reports/{report_id}")
    public ResponseEntity<Report> putReport(@RequestBody QueryCriteria query_criteria, @PathVariable(name = "report_id") long report_id){
        Report report = reportsService.getReport(report_id);
        if (report == null){
            report = new Report();
            report.setReport_id(report_id);
        }
        report.setQuery_criteria_character_phrase(query_criteria.getQuery_criteria_character_phrase());
        report.setQuery_criteria_planet_phrase(query_criteria.getQuery_criteria_planet_phrase());

        if (reportsService.addReport(report)){
            return new ResponseEntity<Report>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Report>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/reports")
    public ResponseEntity deleteAllReports(){
        reportsService.deleteAllReports();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/reports/{report_id}")
    public ResponseEntity deleteReport(@PathVariable(name = "report_id") long report_id){
        reportsService.deleteReport(report_id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("reports/{report_id}")
    public ResponseEntity<Report> getReport(@PathVariable(name = "report_id") long report_id){
        Report report = reportsService.getReport(report_id);
        if (report == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(report);
        }

    }

    @GetMapping("/reports")
    public ResponseEntity<List<Report>> getAllReports(){
        List<Report> reports = reportsService.allReports();
        if (reports == null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(reports);
        }

    }

}
