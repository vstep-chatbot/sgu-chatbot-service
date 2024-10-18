package com.sguchatbot.backend.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Document(collection = "contestants")
public class Contestant extends Record {

    private static final String[] DATE_LABELS = {"Ngày Thi"};

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Field("competition_date")
    public LocalDate competitionDate;

    public Contestant(String importFrom, Map<String, String> data, LocalDateTime importTime) throws IOException {
        super(importFrom, "contestant", data, importTime);

        for (String label : DATE_LABELS) {
            if (data.containsKey(label)) {
                this.competitionDate = LocalDate.parse(data.get(label), formatter);
                break;
            }
        }

        if (this.competitionDate == null) {
            throw new IOException("No competition date found in data");
        }
    }
}
