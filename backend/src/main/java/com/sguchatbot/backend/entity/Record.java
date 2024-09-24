package com.sguchatbot.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;

@Document
@Getter
@NoArgsConstructor
public class Record {
    public String id;

    private String import_from;

    private String type;

    @CreatedDate
    private LocalDateTime import_date;

    private HashMap<String, String> data;

    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", file_id='" + import_from + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
