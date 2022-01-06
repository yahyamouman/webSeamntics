package com.web.webseamntics;

import com.web.webseamntics.services.UploadDataService;
import org.apache.jena.base.Sys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.InputStream;

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

            //Resource resource = new ClassPathResource("initData");
            //File file = resource.getFile();
            // Loading data at the start of the app
            //uploadDataService.loadFromUriAndType(file,"ttl");

        };
    }
}
