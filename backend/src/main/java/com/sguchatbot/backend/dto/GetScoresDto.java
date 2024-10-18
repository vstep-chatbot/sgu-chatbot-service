package com.sguchatbot.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class GetScoresDto {

    public Id _id;
    public int count;
    public List<GetRecordsDto.RecordDto> records;

    public static class Id {
        public String filename;

        public LocalDateTime competition_date;
    }
}