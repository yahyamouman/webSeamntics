package com.web.webseamntics.services;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.web.webseamntics.models.SensorObservation;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WebScrapingServices {

    public ArrayList<SensorObservation> webScrapOutsideObservations(HashSet<String> datesToScrap) {

        ArrayList<SensorObservation> outsideObservations = new ArrayList<>();

        for(String date:datesToScrap){
            String year = date.split("-")[0];
            String month = date.split("-")[1];
            String day = date.split("-")[2];

            WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);

            String searchUrl = "https://www.meteociel.fr/temps-reel/obs_villes.php?code2=7475&jour2=" + day + "&mois2=" + month + "&annee2=" + year;

            try {
                HtmlPage page = client.getPage(searchUrl);
                for (int hour = 0; hour < 24; hour++) {

                    String time = hour>9 ? hour+":00:00" : "0"+hour+":00:00";
                    String xsdDate = year+"-"+month+"-"+day+"T"+time;

                    String temperatureXpath = String.format("//tr[td[text()[contains(.,'%s h')]]]/td[5]/div", hour);
                    String humidityXpath = String.format("//tr[td[text()[contains(.,'%s h')]]]/td[6]/div", hour);
                    String pressureXpath = String.format("//tr[td[text()[contains(.,'%s h')]]]/td[11]/div", hour);

                    HtmlDivision tempAnchor = page.getFirstByXPath(temperatureXpath);
                    HtmlDivision humidAnchor = page.getFirstByXPath(humidityXpath);
                    HtmlDivision pressAnchor = page.getFirstByXPath(pressureXpath);

                    SensorObservation tempObs= new SensorObservation("","emse/fayol","outside-temperature","temperature-sensor",xsdDate);
                    SensorObservation humidityObs= new SensorObservation("","emse/fayol","outside-humidity","humidity-sensor",xsdDate);
                    SensorObservation pressureObs= new SensorObservation("","emse/fayol","outside-pressure","pressure-sensor",xsdDate);

                    tempObs.setValue(tempAnchor.asText().strip().split(" ")[0]);
                    humidityObs.setValue(humidAnchor.asText().strip().split("%")[0]);
                    pressureObs.setValue(pressAnchor.asText().strip().split(" ")[0]);

                    outsideObservations.add(tempObs);
                    outsideObservations.add(humidityObs);
                    outsideObservations.add(pressureObs);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return outsideObservations;
    }
}
