package com.sguchatbot.backend.service;

import com.sguchatbot.backend.entity.Contestant;
import com.sguchatbot.backend.entity.Record;
import lombok.extern.slf4j.Slf4j;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

@Component
@Slf4j
public class ContestantService implements Excel {

    private final Utils utils;

    @Autowired
    public ContestantService(Utils utils) {
        this.utils = utils;
    }

    @Override
    public List<Record> readExcel(String fileLocation) throws IOException {
        List<Record> contestants = new LinkedList<>();

        try (FileInputStream file = new FileInputStream(fileLocation); ReadableWorkbook wb = new ReadableWorkbook(file)) {
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
                            Record contestant = new Contestant(fileLocation, row_data);
                            contestant.type = "contestant";
                            contestants.add(contestant);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return contestants;
    }
}
