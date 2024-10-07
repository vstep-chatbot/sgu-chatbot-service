package com.sguchatbot.backend;

import com.sguchatbot.backend.entity.Record;
import com.sguchatbot.backend.service.ContestantService;
import com.sguchatbot.backend.service.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
public class BackendApplication {


    public static void main(String[] args) throws IOException {
        SpringApplication.run(BackendApplication.class, args);

        Utils utils = new Utils();

        ContestantService test = new ContestantService(utils);


        String currentWorkingDir = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentWorkingDir);

        List<Record> data = test.readExcel("backend/src/main/resources/temp/contestants/VSTEP 20-06.xlsx");

        int counter = 0;
        for (Record record : data) {
            System.out.println(record);
            counter++;
        }
    }

}
