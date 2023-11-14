package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.FullTrainerDto;
import com.novi.gymmanagementapi.dto.PartialMemberDto;
import com.novi.gymmanagementapi.dto.PartialTrainerDto;
import com.novi.gymmanagementapi.services.TrainerService;
import com.novi.gymmanagementapi.utilties.UriBuilder;
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
    public ResponseEntity<List<PartialTrainerDto>> getAllTrainers() {
        return ResponseEntity.ok().body(trainerService.getTrainers());
    }

    @PutMapping("personalTrainers")
    public ResponseEntity<FullTrainerDto> getPersonalTrainer(Principal principal,
                                                             @RequestParam String email) {
        trainerService.assignTrainer(principal.getName(), email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("personalTrainers")
    public ResponseEntity<Object> dismissPersonalTrainers(Principal principal) {
        trainerService.dismissTrainer(principal.getName());
        return ResponseEntity.noContent().build();
    }

    /* BELOW IS FOR AUTHENTICATED TRAINER */

    @GetMapping("trainers")
    public ResponseEntity<PartialTrainerDto> getTrainerAccount(Principal principal) {
        return ResponseEntity.ok(trainerService.getTrainerAccount(principal.getName()));
    }

    @PutMapping("trainers")
    public ResponseEntity<PartialTrainerDto> updateTrainerAccount(Principal principal,
                                                                  @Valid @RequestBody FullTrainerDto dto) {
        return ResponseEntity.ok().body(trainerService.updateTrainer(principal.getName(), dto));
    }

    @GetMapping("trainers/clients")
    public ResponseEntity<List<PartialMemberDto>> getTrainerClients(Principal principal) {
        return ResponseEntity.ok().body(trainerService.getClients(principal.getName()));
    }

    /* BELOW IS FOR AUTHENTICATED ADMIN */

    @GetMapping("admin/trainers")
    public ResponseEntity<PartialTrainerDto> getTrainerAccount(@RequestParam String email) {
        return ResponseEntity.ok(trainerService.getTrainerAccount(email));
    }

    @PostMapping("admin/trainers")
    public ResponseEntity<FullTrainerDto> createTrainerAccount(@RequestBody FullTrainerDto dto) {
        PartialTrainerDto trainerDto = trainerService.createTrainerAccount(dto);
        return ResponseEntity.created(uriBuilder.buildWithEmail(trainerDto.getEmail())).build();
    }

    @PutMapping("admin/trainers")
    public ResponseEntity<PartialTrainerDto> updateTrainerAccount(@RequestParam String email,
                                                                  @Valid @RequestBody FullTrainerDto dto) {
        return ResponseEntity.ok().body(trainerService.updateTrainer(email, dto));
    }

    @DeleteMapping("admin/trainers")
    public ResponseEntity<Object> deleteTrainerAccount(@RequestParam String email) {
        trainerService.dismissTrainerAccount(email);
        return ResponseEntity.noContent().build();
    }

}
