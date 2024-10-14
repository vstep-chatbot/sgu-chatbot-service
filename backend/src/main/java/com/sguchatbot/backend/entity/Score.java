package com.sguchatbot.backend.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@Document(collection = "scores")
public class Score extends Record {

    public LocalDate competition_date;

    public Score(String import_from, Map<String, String> data, LocalDate competition_date) throws IOException {
        super(import_from, "score", data);
        this.competition_date = competition_date;
    }
}
