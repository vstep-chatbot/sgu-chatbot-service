package com.sguchatbot.backend.controller;

import com.sguchatbot.backend.entity.Contestant;
import com.sguchatbot.backend.service.ContestantService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/storage")
@Slf4j
public class FileUploadController {

    private final StorageService storageService;
    private final ContestantService contestantService;

    @Autowired
    public FileUploadController(StorageService storageService, ContestantService contestantService) {
        this.storageService = storageService;
        this.contestantService = contestantService;
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

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("record_type") String recordType,
                                   RedirectAttributes redirectAttributes) {
        log.info("Uploading file: " + file.getOriginalFilename());
        storageService.store(file);
        log.info("File uploaded");
        try {
            if (recordType.equals("contestants")) {

                log.info("Reading contestants from excel");
                List<Contestant> records;
                records = contestantService.readExcel(file.getInputStream(), file.getOriginalFilename());

                log.info("Saving " + records.size() + " contestants to database");

                contestantService.saveContestants(records);

            } else if (recordType.equals("scores")) {
                log.info("Reading scores from excel");
                // TODO: Implement score reading
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid record type, must be 'contestant' or 'scores'");
            }


        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while reading excel", e);
        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}