package com.sguchatbot.backend.entity;

import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Slf4j
public class Record {
    private static final String[] CITIZEN_ID_LABELS = {"cmnd", "cccd"};

    @MongoId(FieldType.OBJECT_ID)
    public String _id;

    @Field("import_from")
    private String importFrom;

    public String type;

    @Setter
    @Field("citizen_id")
    private String citizenId;

    @Field("import_time")
    private LocalDateTime importTime;

    private Map<String, String> data;

    public Record(String importFrom, String type, Map<String, String> data, LocalDateTime importTime) throws IOException {
        this.importFrom = importFrom;
        this.type = type;
        this.data = data;
        this.importTime = importTime;

        for (String label : CITIZEN_ID_LABELS) {
            data.forEach((key, value) -> {
                if (key.toLowerCase().contains(label)) {
                    this.citizenId = value;
                }
            });
        }

        if (this.citizenId == null) {
            log.info(data.toString());
            throw new IOException("No citizen ID found in data");
        }
    }

    @Override
    public String toString() {
        return "Record{" +
                "id='" + _id + '\'' +
                ", file_id='" + importFrom + '\'' +
                ", type='" + type + '\'' +
                ", citizen_id='" + citizenId + '\'' +
                ", import_date=" + importTime +
                ", data=" + data +
                '}';
    }
}
