package com.web.webseamntics.services;

import com.web.webseamntics.models.TripletDisplay;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public final class SparqlService {

    @Value( "${url.data.source}" )
    private  String url;

    @Value( "${dataset}" )
    private  String dataset;




    //connection to the Apache Jena Fuseki
    public RDFConnection conneg;

    public void connect(){
        String SPARQL_END_POINT = url+dataset+"/sparql";
        String SPARQL_UPDATE = url+dataset+"/update";
        String GRAPH_STORE = url+dataset+"/data";
        conneg = RDFConnectionFactory.connect(SPARQL_END_POINT,SPARQL_UPDATE,GRAPH_STORE);
    }

    public TripletDisplay getModelBySubject(String subject)  {
        connect();
        TripletDisplay tripletDisplay = new TripletDisplay(subject);
        String query = "SELECT  ?pre ?object WHERE { "+subject+" ?pre ?object .}";
        QueryExecution qExec = conneg.query(query);
        ResultSet rs = qExec.execSelect();

        while(rs.hasNext()) {

            QuerySolution qs = rs.next();
            String predicate =  qs.get("pre").toString();
            RDFNode object = qs.get("object");

            // adding the predicate and the object
            tripletDisplay.add( predicate , object );

        }
        qExec.close();

        return tripletDisplay;
    }


    public RDFConnection getConnection() {
        connect();
        return conneg;
    }
}
