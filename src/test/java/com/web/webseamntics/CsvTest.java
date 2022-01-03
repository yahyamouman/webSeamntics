package com.web.webseamntics;

import com.opencsv.exceptions.CsvException;
import com.web.webseamntics.services.SensorObservationServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootTest
public class CsvTest {

    @Autowired
    SensorObservationServices sensorObservationServices;


    @Test
    public void csvToTurtleTest() throws IOException, CsvException {
        String fileName = "C:\\Users\\yahya\\IdeaProjects\\webSeamntics\\src\\main\\resources\\static\\sensorData\\20211116-daily-sensor-measures.csv";
        sensorObservationServices.measurementsCsvToObservations(fileName);

        /*String exportFile = "C:\\Users\\yahya\\IdeaProjects\\webSeamntics\\src\\main\\resources\\static\\sensorData\\export.ttl";

        try {
            File myObj = new File(exportFile);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(exportFile);
            myWriter.write(ttl);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }*/

    }
}
