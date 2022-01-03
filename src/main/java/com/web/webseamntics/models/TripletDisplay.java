package com.web.webseamntics.models;

import org.apache.jena.rdf.model.RDFNode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *          Model to encapsulate all triplets of a ``subject``
 *              in order to display it into the web app
 *
 */
public class TripletDisplay {
    // keep trace of the subject
    private String subject;
    // Description
    private String comment;
    //Labels
    private ArrayList<String> labelList = new ArrayList<>();

    // Map of predicate and all its objects
    private HashMap<String, ArrayList<RDFNode>> predicateObject;

    /**
     * Constructor
     * @param subject
     */
    public TripletDisplay(String subject){
        this.subject = subject;
        this.predicateObject = new HashMap<>();
    }

    /**
     * method to add predicate and its objects to the current subject instance
     * @param predicate
     * @param object
     */
    public void add(String predicate , RDFNode object){
        if ( predicate.endsWith("comment") ){
            this.comment = object.toString();
        }
        // if it is a label
        if ( predicate.endsWith("label") )
            this.labelList.add(object.toString());

        // if it is a new object for an existent predicate
        if ( this.predicateObject.containsKey(predicate) )
            this.predicateObject.get(predicate).add(object);
        // if it is a new predicate
        else{
            ArrayList<RDFNode> listObjects = new ArrayList();
            listObjects.add(object);
            this.predicateObject.put(predicate,listObjects);
        }

    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public HashMap<String, ArrayList<RDFNode>> getPredicateObject() {
        return predicateObject;
    }

    public void setPredicateObject(HashMap<String, ArrayList<RDFNode>> predicateObject) {
        this.predicateObject = predicateObject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ArrayList<String> getLabelList() {
        return labelList;
    }

    public void setLabelList(ArrayList<String> labelList) {
        this.labelList = labelList;
    }
}

