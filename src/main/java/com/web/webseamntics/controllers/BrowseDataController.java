package com.web.webseamntics.controllers;


import com.web.webseamntics.models.TripletDisplay;
import com.web.webseamntics.services.SparqlService;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class BrowseDataController {
    @Autowired
    SparqlService sparqlService;

    @GetMapping("/browseData")
    public String start(@RequestParam(value = "subject" ,defaultValue = "<https://territoire.emse.fr/kg/emse/>") String subject , Model model) {

        TripletDisplay tripletDisplay = sparqlService.getModelBySubject(subject);

        model.addAttribute("display",tripletDisplay);
        for (Map.Entry<String , ArrayList<RDFNode>> entry : tripletDisplay.getPredicateObject().entrySet())
            System.out.println(entry.getKey() +" "+ entry.getValue().size());

        return "display";
    }

}