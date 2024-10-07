package com.sguchatbot.backend.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
public class Contestant extends Record {
    public Contestant(String import_from, Map<String, String> data) {
        super(import_from, "contestant", data);
    }
}
