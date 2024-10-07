package com.sguchatbot.backend.service;

import com.sguchatbot.backend.entity.Record;

import java.io.IOException;
import java.util.List;

public interface Excel {
    List<Record> readExcel(String fileLocation) throws IOException;
}
