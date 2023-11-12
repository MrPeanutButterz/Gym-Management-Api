package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.helpers.UriBuilder;
import com.novi.gymmanagementapi.services.TrainerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TrainerController {

    UriBuilder uriBuilder = new UriBuilder();
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }


}
