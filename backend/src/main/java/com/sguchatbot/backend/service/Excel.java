package com.sguchatbot.backend.service;

import com.sguchatbot.backend.entity.Record;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface Excel {
    List<Record> readExcel(InputStream file, String fileName) throws IOException;
}
