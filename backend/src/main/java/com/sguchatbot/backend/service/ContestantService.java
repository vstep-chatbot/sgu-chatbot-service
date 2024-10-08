package com.sguchatbot.backend.service;

import com.sguchatbot.backend.dto.GetContestantsDto;
import com.sguchatbot.backend.entity.Contestant;
import com.sguchatbot.backend.entity.Record;
import com.sguchatbot.backend.repository.ContestantRepository;
import lombok.extern.slf4j.Slf4j;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

@Component
@Slf4j
public class ContestantService {

    private final Utils utils;

    private final ContestantRepository contestantRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ContestantService(Utils utils, ContestantRepository contestantRepository, MongoTemplate mongoTemplate) {
        this.utils = utils;
        this.contestantRepository = contestantRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Contestant> readExcel(InputStream file, String fileName) throws IOException {
        List<Contestant> contestants = new LinkedList<>();

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
                            Contestant contestant = new Contestant(fileName, row_data);
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

    public List<GetContestantsDto> getContestants() {
        // Define the group operation
        GroupOperation groupOperation = Aggregation.group("import_from") // Grouping by 'import_from' field
                .count().as("count") // Counting the number of documents in each group
                .push("$$ROOT").as("records"); // Pushing the entire document into 'records' array

        // Build the aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(groupOperation);

        // Execute the aggregation
        AggregationResults<GetContestantsDto> results = mongoTemplate.aggregate(
                aggregation,
                "vstep-information", // Replace with your actual collection name
                GetContestantsDto.class
        );

        // Return the mapped results
        return results.getMappedResults();
    }

    public void saveContestants(List<Contestant> contestants) {
        contestantRepository.saveAll(contestants);
    }
}
