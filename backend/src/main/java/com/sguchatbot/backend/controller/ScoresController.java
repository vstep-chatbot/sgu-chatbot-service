package com.sguchatbot.backend.controller;

import com.sguchatbot.backend.dto.GetRecordsDto;
import com.sguchatbot.backend.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/scores")
public class ScoresController {

    private final ScoreService scoreService;

    @Autowired
    public ScoresController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("")
    public ResponseEntity<List<GetRecordsDto>> getScores() {
        return ResponseEntity.ok(scoreService.getScores());
    }
}
