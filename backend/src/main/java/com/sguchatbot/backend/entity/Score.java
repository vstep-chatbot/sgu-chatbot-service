package com.sguchatbot.backend.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;
import java.util.Map;

@Document(collection = "scores")
public class Score extends Record {
    public Score(String import_from, Map<String, String> data) throws IOException {
        super(import_from, "score", data);
    }
}
