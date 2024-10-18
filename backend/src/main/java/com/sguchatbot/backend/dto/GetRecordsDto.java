package com.sguchatbot.backend.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GetRecordsDto {

    public List<RecordDto> records;

    public String _id;
    private String filename;

    public int count;

    public GetRecordsDto(String _id, int count, List<RecordDto> records) {
        this._id = _id;
        this.filename = _id;
        this.count = count;
        this.records = records;
    }

    public static class RecordDto {
        public String _id;
        public Map<String, Object> data;
    }
}