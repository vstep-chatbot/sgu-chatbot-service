package com.sguchatbot.backend.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Document(collection = "contestants")
public class Contestant extends Record {

    private static final String[] DATE_LABELS = {"Ngày Thi"};

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public LocalDate competition_date;

    public Contestant(String import_from, Map<String, String> data) throws IOException {
        super(import_from, "contestant", data);

        for (String label : DATE_LABELS) {
            if (data.containsKey(label)) {
                this.competition_date = LocalDate.parse(data.get(label), formatter);
                break;
            }
        }

        if (this.competition_date == null) {
            throw new IOException("No competition date found in data");
        }
    }
}
