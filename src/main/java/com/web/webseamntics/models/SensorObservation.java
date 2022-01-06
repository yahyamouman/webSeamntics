package com.web.webseamntics.models;

import java.util.HashMap;
import java.util.UUID;

public class SensorObservation {
    private static HashMap<String,String> uriAdapter = new HashMap<>();

    static {
        uriAdapter.put("emse/fayol/e4/S405","https://territoire.emse.fr/kg/emse/fayol/4ET/405");
        uriAdapter.put("emse/fayol/e4/S416","https://territoire.emse.fr/kg/emse/fayol/4ET/416");
        uriAdapter.put("emse/fayol/e4/S421","https://territoire.emse.fr/kg/emse/fayol/4ET/421");
        uriAdapter.put("emse/fayol/e4/S422","https://territoire.emse.fr/kg/emse/fayol/4ET/422");
        uriAdapter.put("emse/fayol/e4/S423","https://territoire.emse.fr/kg/emse/fayol/4ET/423");
        uriAdapter.put("emse/fayol/e4/S424","https://territoire.emse.fr/kg/emse/fayol/4ET/424");
        uriAdapter.put("emse/fayol/e4/S425","https://territoire.emse.fr/kg/emse/fayol/4ET/425");
        uriAdapter.put("emse/fayol/e4/S431F","https://territoire.emse.fr/kg/emse/fayol/4ET/431F");
        uriAdapter.put("emse/fayol/e4/S431H","https://territoire.emse.fr/kg/emse/fayol/4ET/431H");
        uriAdapter.put("emse/fayol/e4/S432","https://territoire.emse.fr/kg/emse/fayol/4ET/432");
        uriAdapter.put("emse/fayol/Mobile1","https://territoire.emse.fr/kg/emse/fayol/Mobile1");
        uriAdapter.put("emse/fayol/Mobile2","https://territoire.emse.fr/kg/emse/fayol/Mobile2");
        uriAdapter.put("emse/fayol/Mobile3","https://territoire.emse.fr/kg/emse/fayol/Mobile3");
        uriAdapter.put("emse/fayol/Mobile4","https://territoire.emse.fr/kg/emse/fayol/Mobile4");
        uriAdapter.put("emse/fayol/Mobile5","https://territoire.emse.fr/kg/emse/fayol/Mobile5");
        uriAdapter.put("emse/fayol","https://territoire.emse.fr/kg/emse/fayol/");
    }

    private String uuid;
    private String location;
    private String value;
    private String type;
    private String sensor;
    private String xsdDate;

    public SensorObservation() {
        this.uuid = String.valueOf(UUID.randomUUID());
    }

    public SensorObservation(String value, String location, String type, String sensor, String xsdDate) {
        this.uuid = String.valueOf(UUID.randomUUID());
        this.location = location;
        this.value = value;
        this.type = type;
        this.sensor = sensor;
        this.xsdDate = xsdDate;
    }

    public String toTtl(){
        String uri = uriAdapter.get(this.location);

        return "\n"+"<"+uri+"/"+sensor+"/observations/"+uuid+">  rdf:type   sosa:Observation ;\n" +
                "  rdfs:label \"observation #"+uuid+"\"@en ;\n" +
                "  sosa:hasFeatureOfInterest  <"+uri+"/> ;\n" +
                "  sosa:observedProperty  <"+uri+"#"+type+"> ;\n" +
                "  sosa:madeBySensor <"+uri+"/"+sensor+"> ;\n" +
                "  sosa:hasResult \""+value+"\"^^xsd:double;\n" +
                "  sosa:resultTime \""+xsdDate+"\"^^xsd:dateTime .\n" +
                "<"+uri+"/"+sensor+"> sosa:madeObservation <"+uri+"/"+sensor+"/observations/"+uuid+">.";
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getXsdDate() {
        return xsdDate;
    }

    public void setXsdDate(String xsdDate) {
        this.xsdDate = xsdDate;
    }
}
