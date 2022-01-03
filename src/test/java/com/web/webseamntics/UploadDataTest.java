package com.web.webseamntics;

import com.web.webseamntics.services.UploadDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
public class UploadDataTest {

    @Autowired
    UploadDataService uploadDataService;

    @Test
    public void upload(){
        File file = new File("/Users/yasser/territoire.emse.fr/kg");
        uploadDataService.loadFromUri(file);

    }


}
