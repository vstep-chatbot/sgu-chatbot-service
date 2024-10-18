package com.sguchatbot.backend.service;

import com.sguchatbot.backend.entity.Record;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface Excel {
    List<Record> readExcel(InputStream file, String fileName) throws IOException;

    String getServiceName();

    void saveRecords(List<Record> records);

    void deleteRecords(String filename);
}
