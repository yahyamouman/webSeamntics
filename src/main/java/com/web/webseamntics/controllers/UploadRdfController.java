package com.web.webseamntics.controllers;

import com.web.webseamntics.services.SparqlService;
import com.web.webseamntics.services.UploadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UploadRdfController {
    @Autowired
    SparqlService sparqlService;
    @Autowired
    UploadDataService uploadDataService;

    @GetMapping("/uploadRdf")
    public String start() {
        return "uploadData";
    }

    @PostMapping("/uploadRdf")
    public String uploadFile(@RequestParam("formFile") MultipartFile imageFile, Model model) {
        try {
            uploadDataService.loadFromMultiPart(imageFile);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("result","failure");
            return "uploadData";
        }
        model.addAttribute("result","success");
        return "uploadData";
    }

}