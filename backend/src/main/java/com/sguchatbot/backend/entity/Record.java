package com.sguchatbot.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class Record {
    private static final List<String> CITIZEN_ID_LABELS = List.of("CMND", "CCCD");
    public String id;

    private String import_from;

    public String type;

    @Setter
    private String citizen_id;

    @CreatedDate
    private LocalDateTime import_date;

    private Map<String, String> data;

    public Record(String import_from, String type, Map<String, String> data) {
        this.import_from = import_from;
        this.type = type;
        this.data = data;

        for (String label : CITIZEN_ID_LABELS) {
            if (data.containsKey(label)) {
                this.citizen_id = data.get(label);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", file_id='" + import_from + '\'' +
                ", type='" + type + '\'' +
                ", citizen_id='" + citizen_id + '\'' +
                ", import_date=" + import_date +
                ", data=" + data +
                '}';
    }
}
