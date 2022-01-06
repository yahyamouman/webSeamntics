package com.web.webseamntics.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.web.webseamntics.models.SensorObservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SensorObservationServices {
    @Autowired
    WebScrapingServices webScrapingServices;

    @Autowired
    UploadDataService uploadDataService;

    public static Calendar epochToCalendar(String epochString) {
        double epoch = Double.parseDouble(epochString);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long) (epoch / 1000000)));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh-mm-ss");

        sdf.setTimeZone(TimeZone.getTimeZone("FR"));

        return calendar;
    }

    public static String epochToXsdDateTime(String epochString){
        double epoch = Double.parseDouble(epochString);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("FR"));
        return sdf.format(new Date((long) (epoch / 1000000)));

    }

    public void measurementsCsvToObservations(String file) throws Exception {
        String exportFile = new File("./src/main/resources/export/RDFMeasurements-"+UUID.randomUUID()+".ttl").getCanonicalPath();

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
        //allObservations.add(obs);
        FileWriter fw = new FileWriter(exportFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.\n" +
                "@prefix xsd: <http://www.w3.org/2001/XMLSchema#>.\n" +
                "@prefix seas:  <https://w3id.org/seas/>.\n" +
                "@prefix bot:   <https://w3id.org/bot#>.\n" +
                "@prefix owl:   <http://www.w3.org/2002/07/owl#>.\n" +
                "@prefix sosa: <http://www.w3.org/ns/sosa/>.\n" +
                "@prefix ssn: <http://www.w3.org/ns/ssn/>.\n" +
                "@prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#>.\n" +
                "@prefix rec: <https://w3id.org/rec/asset>.\n" +
                "@prefix om: <http://www.ontology-of-units-of-measure.org/resource/om-2/>.\n");

        ArrayList<SensorObservation> allObservations = new ArrayList<>();

        HashSet<String> datesToScrap = new HashSet<>();

        try (CSVReader reader = new CSVReader(new FileReader(file))) {

            String [] x;
            String[] header = reader.readNext(); //remove header
            while ((x = reader.readNext()) != null) {
                    String epochTime = x[1];

                    // get all dates for scraping
                    Calendar calendar = epochToCalendar(epochTime);
                    String day = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                    String month = String.valueOf(calendar.get(Calendar.MONTH));
                    String year = String.valueOf(calendar.get(Calendar.YEAR));
                    String date = year + "-" + month + "-" + day;
                    datesToScrap.add(date);

                    String value = "";
                    String uri = "";
                    String type = "";
                    String sensor = "";
                    String xsdDate = "";

                    //use the current line to create an observation object and add it to observations list
                    xsdDate = epochToXsdDateTime(x[1]);
                    uri = x[9];
                    if (!x[2].equals("")) {
                        value = x[2];
                        type = "humidity";
                        sensor = "humidity-sensor";
                        SensorObservation obs = new SensorObservation(value, uri, type, sensor, xsdDate);
                        //allObservations.add(obs);
                        bw.write(obs.toTtl());
                    }
                    if (!x[3].equals("")) {
                        value = x[3];
                        type = "luminosity";
                        sensor = "luminosity-sensor";
                        SensorObservation obs = new SensorObservation(value, uri, type, sensor, xsdDate);
                        //allObservations.add(obs);
                        bw.write(obs.toTtl());
                    }
                    if (!x[4].equals("")) {
                        value = x[4];
                        type = "SND";
                        sensor = "SND-sensor";
                        SensorObservation obs = new SensorObservation(value, uri, type, sensor, xsdDate);
                        //allObservations.add(obs);
                        bw.write(obs.toTtl());
                    }
                    if (!x[5].equals("")) {
                        value = x[5];
                        type = "SDNF";
                        sensor = "SNDF-sensor";
                        SensorObservation obs = new SensorObservation(value, uri, type, sensor, xsdDate);
                        //allObservations.add(obs);
                        bw.write(obs.toTtl());
                    }
                    if (!x[6].equals("")) {
                        value = x[6];
                        type = "SNDM";
                        sensor = "SNDM-sensor";
                        SensorObservation obs = new SensorObservation(value, uri, type, sensor, xsdDate);
                        //allObservations.add(obs);
                        bw.write(obs.toTtl());
                    }
                    if (!x[7].equals("")) {
                        value = x[7];
                        type = "temperature";
                        sensor = "temperature-sensor";
                        SensorObservation obs = new SensorObservation(value, uri, type, sensor, xsdDate);
                        //allObservations.add(obs);
                        bw.write(obs.toTtl());
                    }


            }
        } catch (IOException | CsvException e) {
            throw new Exception();
        }

        //loop over outsideObservations set and convert to ttl
        allObservations.addAll(webScrapingServices.webScrapOutsideObservations(datesToScrap));
        for(SensorObservation obs:allObservations){
            bw.write(obs.toTtl());

        }
        bw.close();

        uploadDataService.loadFromPath(exportFile);
    }

    public void writeToFile(SensorObservation sensorObservation,String fileName){

    }

    public String ObservationsToTurtle(ArrayList<SensorObservation> observations){
        String ttl = "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.\n" +
                "@prefix xsd: <http://www.w3.org/2001/XMLSchema>.\n" +
                "@prefix seas:  <https://w3id.org/seas/>.\n" +
                "@prefix bot:   <https://w3id.org/bot#>.\n" +
                "@prefix owl:   <http://www.w3.org/2002/07/owl#>.\n" +
                "@prefix sosa: <http://www.w3.org/ns/sosa/>.\n" +
                "@prefix ssn: <http://www.w3.org/ns/ssn/>.\n" +
                "@prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#>.\n" +
                "@prefix rec: <https://w3id.org/rec/asset>.\n" +
                "@prefix om: <http://www.ontology-of-units-of-measure.org/resource/om-2/>.\n";

        for (SensorObservation sensorObservation:observations){
            ttl+=sensorObservation.toTtl();
        }

        return ttl;
    }


}
