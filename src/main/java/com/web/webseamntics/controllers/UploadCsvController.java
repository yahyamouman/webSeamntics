package com.web.webseamntics.controllers;

import com.opencsv.exceptions.CsvException;
import com.web.webseamntics.services.SensorObservationServices;
import com.web.webseamntics.services.SparqlService;
import com.web.webseamntics.services.UploadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UploadCsvController {
    @Autowired
    SparqlService sparqlService;
    @Autowired
    SensorObservationServices sensorObservationServices;

    @GetMapping("/uploadCsv")
    public String start() {
        return "uploadCsv";
    }

    @PostMapping("/uploadCsv")
    public String uploadFile(@RequestParam("formFile") MultipartFile multipartFile, Model model) {
        File convFile = null;
        try {
            String uniqueID = UUID.randomUUID().toString();
            convFile = new File(uniqueID+multipartFile.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(multipartFile.getBytes());
            fos.close();
            sensorObservationServices.measurementsCsvToObservations(convFile.getAbsolutePath());
        } catch (Exception e) {

            model.addAttribute("result","failure");
            return "uploadCsv";
        }finally {
            if ( convFile != null )
                convFile.delete();
        }
        model.addAttribute("result","success");
        return "uploadCsv";
    }

}