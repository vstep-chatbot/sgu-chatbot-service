package com.sguchatbot.backend.controller;

import com.sguchatbot.backend.dto.GetContestantsDto;
import com.sguchatbot.backend.entity.Contestant;
import com.sguchatbot.backend.service.ContestantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contestants")
public class ContestantsController {

    private final ContestantService contestantService;

    @Autowired
    public ContestantsController(ContestantService contestantService) {
        this.contestantService = contestantService;
    }

    @GetMapping("/")
    public ResponseEntity<List<GetContestantsDto>> getContestants() {
        return ResponseEntity.ok(contestantService.getContestants());
    }
}
