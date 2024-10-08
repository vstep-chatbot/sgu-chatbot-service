package com.sguchatbot.backend.service;

import com.sguchatbot.backend.entity.Contestant;
import com.sguchatbot.backend.entity.File;

import com.sguchatbot.backend.entity.Score;
import com.sguchatbot.backend.repository.ScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

import com.sguchatbot.backend.entity.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScoreService implements Excel {

    private final Utils utils;
    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreService(Utils utils, ScoreRepository scoreRepository) {
        this.utils = utils;
        this.scoreRepository = scoreRepository;
    }

    public List<Record> readExcel(InputStream file, String fileName) throws IOException {
        List<Record> scores = new LinkedList<>();

        try (ReadableWorkbook wb = new ReadableWorkbook(file)) {
            wb.getSheets().forEach(sheet -> {
                try (Stream<Row> rows = sheet.openStream()) {
                    LinkedList<String> labels = new LinkedList<>();

                    Iterator<Row> iter = rows.iterator();

                    Row firstRow = iter.next();

                    for (Cell cell : firstRow) {
                        if (cell == null) {
                            break;
                        }
                        String converted = utils.toUpperCamelCase(cell.getRawValue());
                        labels.add(converted);
                    }

                    while (iter.hasNext()) {
                        Row r = iter.next();
                        Map<String, String> row_data = new HashMap<>();
                        boolean allNull = true;

                        Iterator<Cell> row_iter = r.iterator();

                        for (String label : labels) {
                            if (row_iter.hasNext()) {
                                Cell cell = row_iter.next();
                                String cellValue = cell.getRawValue();

                                row_data.put(label, cellValue);

                                if (cellValue != null) {
                                    allNull = false;
                                }
                            } else break;
                        }
                        if (allNull) {
                            log.info("Sheet " + sheet.getName() + ": Empty row detected, skipping...");
                            break;
                        } else {
                            Score score = new Score(fileName, row_data);
                            scores.add(score);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return scores;
    }
}
