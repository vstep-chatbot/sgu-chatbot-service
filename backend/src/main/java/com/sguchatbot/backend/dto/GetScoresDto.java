package com.sguchatbot.backend.dto;

import java.time.LocalDate;

public class GetScoresDto extends GetRecordsDto {
    public Id _id;

    private static class Id {
        public String import_from;
        public LocalDate competition_date;
    }
}