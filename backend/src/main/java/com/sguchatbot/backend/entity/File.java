package com.sguchatbot.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class File {
    private String id;

    private String filename;

    private Integer record_count;

    @CreatedDate
    private LocalDateTime created_at;

    @Override
    public String toString() {
        return "File{" +
                "id='" + id + '\'' +
                ", filename='" + filename + '\'' +
                ", record_count=" + record_count +
                ", created_at=" + created_at +
                '}';
    }
}
