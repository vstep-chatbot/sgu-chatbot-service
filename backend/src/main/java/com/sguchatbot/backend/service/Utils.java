package com.sguchatbot.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class Utils {

    private static final Pattern datePattern = Pattern.compile("\\b\\d+/\\d+/\\d+\\b");

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

    public LocalDate parseDateFromString(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            return null;
        }

        Matcher matcher = datePattern.matcher(sentence);

        if (matcher.find()) {
            log.info("Found date: " + matcher.group());
            String date = matcher.group();
            String[] parts = date.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return LocalDate.of(year, month, day);
        }

        return null;
    }
}
