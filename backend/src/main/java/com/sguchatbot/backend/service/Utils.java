package com.sguchatbot.backend.service;

import org.springframework.stereotype.Component;

@Component
public class Utils {
    public String toUpperCamelCase(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        String[] parts = value.split("\\s+");
        StringBuilder camelCaseString = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                camelCaseString.append(part.substring(0, 1).toUpperCase())
                        .append(part.substring(1).toLowerCase());
            }
            if (camelCaseString.length() != value.length()) {
                camelCaseString.append(" ");
            }
        }
        return camelCaseString.toString();
    }
}
