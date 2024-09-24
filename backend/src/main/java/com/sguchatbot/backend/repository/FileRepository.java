package com.sguchatbot.backend.repository;

import com.sguchatbot.backend.entity.File;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FileRepository extends MongoRepository<File, String> {

    List<File> findAllByFilename(String filename);

}