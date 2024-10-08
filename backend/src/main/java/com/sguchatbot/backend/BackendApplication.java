package com.sguchatbot.backend;

import com.sguchatbot.backend.entity.Record;
import com.sguchatbot.backend.service.ContestantService;
import com.sguchatbot.backend.service.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
@EnableMongoAuditing
public class BackendApplication {


    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
