package com.web.webseamntics;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.vocabulary.VCARD;

public class Main {
    public static void main(String[] args){
        Model model = ModelFactory.createDefaultModel();
        // some definitions
         String personURI    = "http://somewhere/JohnSmith";
         String fullName     = "John Smith";
        Resource johnSmith =
                model.createResource(personURI)
                        .addProperty(VCARD.FN, fullName);

        // ... build the model
        String datasetURL = "http://localhost:3030/territoire";
        String sparqlEndpoint = datasetURL + "/sparql";
        String sparqlUpdate = datasetURL + "/update";
        String graphStore = datasetURL + "/data";
        RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint,sparqlUpdate,graphStore);
        conneg.load(model); // add the content of model to the triplestore
        conneg.update("INSERT DATA { <test> a <TestClass> }"); // add the triple to the triplestore
    }
}
