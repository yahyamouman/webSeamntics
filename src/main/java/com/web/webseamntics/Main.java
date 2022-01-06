package com.web.webseamntics;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.VCARD;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public final static String SPARQL_END_POINT = "http://localhost:3030/territoire/sparql";
    public final static String SPARQL_UPDATE = "http://localhost:3030/territoire/update";
    public final static String GRAPH_STORE = "http://localhost:3030/territoire/data";

    //connection to the Apache Jena Fuseki
    public static RDFConnection conneg = RDFConnectionFactory.connect(SPARQL_END_POINT,SPARQL_UPDATE,GRAPH_STORE);

    public static void loadFromUri(File file){

        // if it is a file we load it to the Jena Db
        if ( ( !file.isDirectory() ) ){
            System.out.println("Inside dir = "+file.getAbsolutePath());
            if ( file.getAbsolutePath().endsWith(".ttl") )
                conneg.load(file.getAbsolutePath()  );
                //conneg.load(RDFDataMgr.loadModel( file.getAbsolutePath() ) );

        }
        // else we loop over files on it
        else
            for ( File file1 : file.listFiles() )
                loadFromUri(file1);

    }

    public static void printDataSPARQL(String subjetVar,String queryString) {
        QueryExecution qExec = conneg.query(queryString);
        ResultSet rs = qExec.execSelect();
        while(rs.hasNext()) {
            QuerySolution qs = rs.next() ;
            Resource subject = qs.getResource(subjetVar);
            System.out.println("Subject: "+subject);
        }
        qExec.close();
    }

    public static boolean isClickable(String subject) {
        if ( conneg == null)
            System.out.println("conneg is null");
        else
            System.out.println("not null");
        // query to count number of result rows
        String query = "SELECT (COUNT(*) AS ?size ) WHERE { "+subject+" ?pre ?object .}";

        // Preparing the QueryExecution
        QueryExecution qExec = conneg.query(query);
        // getting results
        ResultSet rs = qExec.execSelect();

        if ( rs.hasNext() ){
            QuerySolution qs = rs.next();

            System.out.println(qs.getLiteral("size").getInt());
        }

        if ( rs.getRowNumber() == 0 )
            return false;

        return true;

    }

    public static void printDataSPARQL(String subjetVar,String subjetVar1,String queryString) {


        // Preparing the QueryExecution
        QueryExecution qExec = conneg.query(queryString);
        // getting results
        ResultSet rs = qExec.execSelect();

        while(rs.hasNext()) {
            QuerySolution qs = rs.next() ;

            RDFNode subject =  qs.get(subjetVar);
            RDFNode subject1 = qs.get(subjetVar1);

            System.out.println("Predicate: "+subject + " is Literal " + subject1.isLiteral() );
            System.out.println("Object: "+subject1 + " is Resource " + subject1.isResource());
        }
        qExec.close();

    }




    public static void main(String[] args){
        //code for loading a directory or a file to the TripleStore
        File file = new File("/Users/yasser/territoire.emse.fr/kg");
        //File file = new File("/Users/yasser/Downloads/SensorDefintion.ttl");
        loadFromUri(file);
        //System.out.println(isClickable("<https://territoire.emse.fr/kg/ontology/>"));
        //printDataSPARQL("pre","object","SELECT  ?pre ?object WHERE { <https://territoire.emse.fr/kg/ontology/> ?pre ?object .}");


    }

}
