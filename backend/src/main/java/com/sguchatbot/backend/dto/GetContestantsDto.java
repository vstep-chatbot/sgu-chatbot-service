package com.sguchatbot.backend.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GetContestantsDto {
    public String import_from; // Represents the _id field in the aggregation

    public int count;

    public List<Map<String, Object>> records;
}

