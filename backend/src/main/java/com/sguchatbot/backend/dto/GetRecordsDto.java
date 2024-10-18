package com.sguchatbot.backend.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GetRecordsDto {
    public String _id;

    public int count;

    public List<Map<String, Object>> records;
}