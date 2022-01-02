package com.web.webseamntics.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String start(Model model) {
        model.addAttribute("exemple","eess");
        System.out.println("Started  :");
        return "index";
    }

}