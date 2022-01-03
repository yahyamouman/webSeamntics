package com.web.webseamntics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class UploadDataService {
    @Autowired
    SparqlService sparqlService;

    public void loadFromUri(File file){

        // if it is a file we load it to the Jena Db
        if ( ( !file.isDirectory() ) ){
            System.out.println("Inside dir = "+file.getAbsolutePath());
            if ( file.getAbsolutePath().endsWith(".ttl") )
                sparqlService.getConnection().load(file.getAbsolutePath()  );
        }
        // else we loop over files on it
        else
            for ( File file1 : file.listFiles() )
                loadFromUri(file1);

    }
}
