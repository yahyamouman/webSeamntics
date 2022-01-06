package com.web.webseamntics.services;

import com.web.webseamntics.exceptions.BadFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadDataService {
    @Autowired
    SparqlService sparqlService;

    public void loadFromUriAndType(File file,String type){
        // if it is a file we load it to the Jena Db
        if ( ( !file.isDirectory() ) ){

            if ( file.getAbsolutePath().endsWith("."+type) ) {
                System.out.println("Loading file to Jena Fuseki = "+file.getAbsolutePath());
                sparqlService.getConnection().load(file.getAbsolutePath());
            }
        }
        // else we loop over files on it
        else
            for ( File file1 : file.listFiles() )
                loadFromUriAndType(file1,type);

    }
    public void loadFromUri(File file){

        // if it is a file we load it to the Jena Db
        if ( ( !file.isDirectory() ) ){
            System.out.println("Inside dir = "+file.getAbsolutePath());
            if ( file.getAbsolutePath().endsWith(".ttl") || file.getAbsolutePath().endsWith(".n3") || file.getAbsolutePath().endsWith(".nt")|| file.getAbsolutePath().endsWith(".rdf"))
                sparqlService.getConnection().load(file.getAbsolutePath());

        }
        // else we loop over files on it
        else
            for ( File file1 : file.listFiles() )
                loadFromUri(file1);

    }
    public void loadFromMultiPart(MultipartFile multipartFile ) throws IOException, BadFileException {
        if ( !(multipartFile.getOriginalFilename().endsWith(".ttl") || multipartFile.getOriginalFilename().endsWith(".n3") || multipartFile.getOriginalFilename().endsWith(".nt") || multipartFile.getOriginalFilename().endsWith(".rdf")|| multipartFile.getOriginalFilename().endsWith(".jsonld") ))
            throw new BadFileException();

        String uniqueID = UUID.randomUUID().toString();
        File convFile = new File(uniqueID+multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        System.out.println(convFile.getAbsolutePath() + " size = " + convFile.length());
        loadFromUri(convFile);
        convFile.delete();

    }
}
