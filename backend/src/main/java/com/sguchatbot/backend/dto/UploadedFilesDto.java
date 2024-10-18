package com.sguchatbot.backend.dto;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class UploadedFilesDto {
    public String filename;
    public LocalDateTime created_at;
}
