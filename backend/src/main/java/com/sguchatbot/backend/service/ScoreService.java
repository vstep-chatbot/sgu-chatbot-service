package com.sguchatbot.backend.service;

import com.sguchatbot.backend.dto.DateHolder;
import com.sguchatbot.backend.dto.GetScoresDto;
import com.sguchatbot.backend.entity.Record;
import com.sguchatbot.backend.entity.Score;
import com.sguchatbot.backend.repository.ScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
@Slf4j
public class ScoreService implements Excel {

    private final Utils utils;
    private final ScoreRepository scoreRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ScoreService(Utils utils, ScoreRepository scoreRepository, MongoTemplate mongoTemplate) {
        this.utils = utils;
        this.scoreRepository = scoreRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public String getServiceName() {
        return "scores";
    }

    /**
     * <h6>Kiểm tra xem một hàng có phải hàng <code>tiêu đề</code> không</h6>
     * <p>Hàng <code>tiêu đề</code> là hàng có:</p>
     * <ul>
     *     <li>Ô đầu tiên không nằm ở cột đầu tiên</li>
     *     <li>Tổng số ô trống >= 50% số ô</li>
     * </ul>
     *
     * @param row The row to check
     * @return Tiêu đề của hàng, <code>null</code>, hoặc <code>""</code>
     */
    private String isTitleRow(Row row) {
        Optional<Cell> firstCell = row.getFirstNonEmptyCell();

        // Empty row
        if (firstCell.isEmpty() || firstCell.get().getRawValue() == null || firstCell.get().getRawValue().isEmpty()) {
            return "";
        }

        // Is title
        else if (firstCell.get().getColumnIndex() > 0) {
            return firstCell.get().getRawValue();

        } else {
            int nullCount = 0;
            for (Cell cell : row) {
                if (cell == null || cell.getRawValue() == null)
                    nullCount++;
            }

            // Is title
            if (nullCount >= (row.getPhysicalCellCount() / 2)) {
                return firstCell.get().getRawValue();
            }

            return null;
        }
    }

    public List<Record> readExcel(InputStream file, String fileName) throws IOException {
        List<Record> scores = new LinkedList<>();
        LocalDateTime importTime = LocalDateTime.now();

        DateHolder competitionDate = new DateHolder();

        try (ReadableWorkbook wb = new ReadableWorkbook(file)) {
            wb.getSheets().forEach(sheet -> {
                try (Stream<Row> rows = sheet.openStream()) {
                    ArrayList<String> labels = new ArrayList<>();
                    LinkedList<Cell> labelCells = new LinkedList<>();

                    Iterator<Row> iter = rows.iterator();

                    Row firstRow = iter.next();

                    log.info("---- " + sheet.getName() + " ----");
//                    Skip the first few rows if it is for the title
                    while (true) {
                        log.info("Checking date in row " + firstRow.getRowNum());

                        String title = isTitleRow(firstRow);

                        if (title != null) {
                            log.info("[Sheet " + sheet.getName() + " - Row " + firstRow.getRowNum() + "]: Title or empty row detected: " + title);
                            if (competitionDate.value == null) competitionDate.value = utils.parseDateFromString(title);
                            firstRow = iter.next();
                            continue;
                        }
                        break;
                    }

                    if (competitionDate.value == null) {
                        log.info("Sheet " + sheet.getName() + ": No date found, skipping...");
                        throw new IOException("No date found in the sheet");
                    }

                    for (Cell cell : firstRow) {
                        if (cell == null) {
                            break;
                        }
                        String converted = utils.toUpperCamelCase(cell.getRawValue());
                        labels.add(converted);
                        labelCells.add(cell);
                    }
                    log.info("Label: " + labels.stream().reduce((a, b) -> a + ", " + b).orElse(""));

                    while (iter.hasNext()) {
                        Row r = iter.next();
                        Map<String, String> rowData = new HashMap<>();
                        boolean allNull = true;

                        Iterator<Cell> row_iter = r.iterator();

                        for (Cell labelCell : labelCells) {
                            String label = labels.get(labelCell.getColumnIndex());
                            if (row_iter.hasNext()) {
                                Cell cell = row_iter.next();
                                String cellValue = cell.getRawValue();

                                // If this label is empty,
                                if (labelCell.getRawValue() == null || labelCell.getRawValue().isEmpty()) {
                                    String prevCellValue = rowData.get(labels.get(labelCell.getColumnIndex() - 1));
                                    rowData.put(labels.get(labelCell.getColumnIndex() - 1), prevCellValue + " " + cellValue);
                                } else rowData.put(label, cellValue);

                                if (cellValue != null) {
                                    allNull = false;
                                }
                            } else break;
                        }
                        if (allNull) {
                            log.info("Sheet " + sheet.getName() + ": Empty row detected, skipping...");
                            break;
                        } else {
                            Score score = new Score(fileName, rowData, importTime, competitionDate.value);
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

    public List<GetScoresDto> getScores() {
        // Define the group operation
        AggregationOperation operation = Aggregation.stage(new Document("$group",
                new Document("_id",
                        new Document("filename", "$import_from")
                                .append("competition_date", "$competition_date"))
                        .append("count",
                                new Document("$sum", 1L))
                        .append("records",
                                new Document("$push",
                                        new Document("_id", "$$ROOT._id")
                                                .append("data", "$$ROOT.data")))));

        // Build the aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(operation);

        // Execute the aggregation
        AggregationResults<GetScoresDto> results = mongoTemplate.aggregate(
                aggregation, "scores", GetScoresDto.class);

        // Return the mapped results
        return results.getMappedResults();
    }

    public void saveRecords(List<Record> records) {
        List<Score> scores = records.stream().map(r -> (Score) r).toList();
        scoreRepository.saveAll(scores);
    }

    public void deleteRecords(String filename) {
        scoreRepository.deleteAllByImportFrom(filename);
    }
}
