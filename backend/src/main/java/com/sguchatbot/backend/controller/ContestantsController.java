package com.sguchatbot.backend.controller;

import com.sguchatbot.backend.service.ContestantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContestantsController {

    private final ContestantService contestantService;

    @Autowired
    public ContestantsController(ContestantService contestantService) {
        this.contestantService = contestantService;
    }
}
