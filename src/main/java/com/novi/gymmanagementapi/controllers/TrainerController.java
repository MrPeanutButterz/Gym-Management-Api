package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.NewTrainer;
import com.novi.gymmanagementapi.dto.TrainerDto;
import com.novi.gymmanagementapi.utilties.UriBuilder;
import com.novi.gymmanagementapi.services.TrainerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api")
public class TrainerController {

    UriBuilder uriBuilder = new UriBuilder();
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    /* BELOW IS FOR AUTHENTICATED MEMBER */

    @GetMapping("personalTrainers")
    public ResponseEntity<List<TrainerDto>> getAllTrainers() {
        return ResponseEntity
                .ok()
                .body(trainerService.getTrainers());
    }

    @PutMapping("personalTrainers")
    public ResponseEntity<TrainerDto> getPersonalTrainer(Principal principal,
                                                         @RequestParam String email) {
        trainerService.assignTrainer(principal.getName(), email);
        return ResponseEntity
                .ok()
                .build();
    }

    // todo add deletePersonalTrainer

    /* BELOW IS FOR AUTHENTICATED TRAINER */

    @GetMapping("trainers")
    public ResponseEntity<TrainerDto> getTrainerAccount(Principal principal) {
        return ResponseEntity
                .ok(trainerService.getTrainerAccount(principal.getName()));
    }

    // todo add updateTrainerAccount

    /* BELOW IS FOR AUTHENTICATED ADMIN */

    @GetMapping("admin/trainers")
    public ResponseEntity<TrainerDto> getTrainerAccount(@RequestParam String email) {
        return ResponseEntity
                .ok(trainerService.getTrainerAccount(email));
    }

    @PostMapping("admin/trainers")
    public ResponseEntity<TrainerDto> createTrainer(@Valid @RequestBody NewTrainer newDto) {
        TrainerDto trainerDto = trainerService.createTrainer(newDto);
        return ResponseEntity
                .created(uriBuilder.buildWithEmail(trainerDto.getEmail()))
                .body(trainerDto);
    }

    // todo add updateTrainerAccount
    // todo add deleteTrainerAccount

}

































/*

    @DeleteMapping("personalTrainer")
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
