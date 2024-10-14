package com.sguchatbot.backend.controller;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/storage")
@Slf4j
public class FileUploadController {

    private final List<Excel> mainServices;
    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService, Excel... services) {
        this.mainServices = List.of(services);
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
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

    @PostMapping("")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("record_type") String recordType) {
        log.info("Uploading file: " + file.getOriginalFilename());
        storageService.store(file);
        log.info("File stored to disk, starting to read content");

        try {
            Excel service = getServive(recordType);
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

    private Excel getServive(String type) throws IllegalArgumentException {
        for (Excel service : mainServices) {
            if (type.equals(service.getServiceName()))
                return service;
        }
        throw new IllegalArgumentException("No service found, except contestants or services");
    }

}