package com.novi.gymmanagementapi.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.novi.gymmanagementapi.dto.TrainerDto;
import com.novi.gymmanagementapi.utilties.ResponseViews;
import com.novi.gymmanagementapi.utilties.UriBuilder;
import com.novi.gymmanagementapi.services.TrainerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class TrainerController {

    UriBuilder uriBuilder = new UriBuilder();
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping("trainers")
    public ResponseEntity<TrainerDto> createTrainer(@Valid @RequestBody TrainerDto newDto) {
        TrainerDto trainerDto = trainerService.createTrainer(newDto);
        return ResponseEntity
                .created(uriBuilder.buildWithEmail(trainerDto.getEmail()))
                .body(trainerDto);
    }
    @GetMapping("trainers")
    @JsonView(ResponseViews.MyResponseView.class)
    public ResponseEntity<List<TrainerDto>> getTrainers() {
        return ResponseEntity
                .ok(trainerService.getTrainers());
    }
/*

    @GetMapping("trainers/details")
    public ResponseEntity<TrainerDto> getTrainer(@RequestParam long trainerID) {
        return ResponseEntity
                .ok()
                .body(trainerService.getTrainer(trainerID));
    }

    @PutMapping("trainers")
    public ResponseEntity<TrainerDto> updateTrainer(@RequestParam long trainerID,
                                                    @Valid @RequestBody TrainerDto trainerDto) {
        TrainerDto dto = trainerService.updateTrainer(trainerID, trainerDto);
        return ResponseEntity
                .created(uriBuilder.buildWithEmail(dto.getEmail()))
                .body(dto);
    }

    @DeleteMapping("trainers")
    public ResponseEntity<Objects> deleteTrainer(@RequestParam long trainerID) {
        trainerService.deleteTrainer(trainerID);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("trainers/assistance")
    public ResponseEntity<TrainerDto> assignTrainer(@RequestParam long trainerID,
                                                    @RequestParam long memberID) {
        TrainerDto trainerDto = trainerService.assignTrainer(trainerID, memberID);
        return ResponseEntity
                .created(uriBuilder.buildWithEmail(trainerDto.getEmail()))
                .body(trainerDto);
    }

    @DeleteMapping("trainers/assistance")
    public ResponseEntity<Objects> dismissTrainer(@RequestParam long memberID) {
        //trainerService.dismissTrainer(memberID);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("trainers/assistance")
    public ResponseEntity<List<Long>> getClientsFromTrainer(@RequestParam long trainerID) {
        return ResponseEntity
                .ok(trainerService.getClientsFromTrainer(trainerID));
    }*/
}
