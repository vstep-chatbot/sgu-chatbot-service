package com.sguchatbot.backend.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "scores")
public class Score extends Record {

    @Field("competition_date")
    public LocalDate competitionDate;

    public Score(String importFrom, Map<String, String> data, LocalDateTime importDate, LocalDate competitionDate) throws IOException {
        super(importFrom, "score", data, importDate);
        this.competitionDate = competitionDate;
    }
}
