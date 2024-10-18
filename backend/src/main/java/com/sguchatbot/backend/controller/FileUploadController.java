package com.sguchatbot.backend.controller;

import com.sguchatbot.backend.dto.UploadedFilesDto;
import com.sguchatbot.backend.entity.Record;
import com.sguchatbot.backend.service.Excel;
import com.sguchatbot.backend.storage.StorageFileNotFoundException;
import com.sguchatbot.backend.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;


@Controller
@RequestMapping("/storage")
@Slf4j
public class FileUploadController {

    private final Map<String, Excel> mainServices;
    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService, Excel... services) {
        this.storageService = storageService;
        this.mainServices = Arrays.stream(services).collect(
                toMap(Excel::getServiceName, service -> service));
    }

    private Excel getService(String type) {
        return mainServices.get(type);
    }

    @GetMapping("/files")
    public ResponseEntity<List<UploadedFilesDto>> listUploadedFiles() {
        return ResponseEntity.ok(storageService.listUploadedFiles());
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @DeleteMapping("/files/{filename:.+}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename, @RequestParam String type) {
        storageService.deleteFile(filename, type);

        try {
            Excel service = getService(type);
            log.info("Deleting [" + service.getServiceName() + "] imported from file [" + filename + "] from database");
            service.deleteRecords(filename);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok("Successfully deleted " + filename);
    }

    @PostMapping("")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("record_type") String recordType) {
        log.info("Uploading file: " + file.getOriginalFilename());
        storageService.store(file, recordType);
        log.info("File stored to disk, starting to read content");

        try {
            Excel service = getService(recordType);
            List<Record> records;

            records = service.readExcel(file.getInputStream(), file.getOriginalFilename());

            log.info("Saving " + records.size() + " " + recordType + "(s) to database");

            service.saveRecords(records);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while reading excel", e);
        }

        return ResponseEntity.ok("Successfully uploaded " + file.getOriginalFilename());
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}