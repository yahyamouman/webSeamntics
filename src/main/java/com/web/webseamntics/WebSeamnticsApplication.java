package com.web.webseamntics;

import com.web.webseamntics.services.UploadDataService;
import org.apache.jena.base.Sys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class WebSeamnticsApplication {
    @Autowired
    UploadDataService uploadDataService;

    public static void main(String[] args) {
        SpringApplication.run(WebSeamnticsApplication.class, args);
    }

    @Bean
    public CommandLineRunner CommandLineRunnerBean() {
        return (args) -> {
            System.out.println(getClass().getResource("../../../initData").toString());
            File file = new File(getClass().getResource("../../../initData").toString());
            uploadDataService.loadFromUri(file);
        };
    }
}
