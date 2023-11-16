package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.ExerciseDto;
import com.novi.gymmanagementapi.services.ExerciseService;
import com.novi.gymmanagementapi.utilties.UriBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api")
public class ExerciseController {


    UriBuilder uriBuilder = new UriBuilder();
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    /* BELOW IS FOR AUTHENTICATED MEMBERS
     * Member can create, update or delete exercises
     * */

    @PostMapping("members/goals/workouts/exercise")
            public ResponseEntity<ExerciseDto> createExercise(Principal principal,
                                                              @RequestParam Long workoutID,
                                                              @Valid @RequestBody ExerciseDto exerciseDto) {
        return ResponseEntity
                .created(uriBuilder.buildWithEmail(principal.getName()))
                .body(exerciseService.createExercise(principal.getName(), workoutID, exerciseDto));
    }

    @GetMapping("members/goals/workouts/exercise")
    public ResponseEntity<List<ExerciseDto>> getExercises(Principal principal, @RequestParam Long workoutID) {
        return ResponseEntity.ok().body(exerciseService.getExercises(principal.getName(), workoutID));
    }

    @PutMapping("members/goals/workouts/exercise")
    public ResponseEntity<ExerciseDto> updateExercise(Principal principal,
                                                    @RequestParam Long exerciseID,
                                                    @Valid @RequestBody ExerciseDto exerciseDto) {
        return ResponseEntity.ok().body(exerciseService.updateExercise(principal.getName(), exerciseID, exerciseDto));
    }

    @DeleteMapping("members/goals/workouts/exercise")
    public ResponseEntity<Object> deleteExercise(Principal principal, @RequestParam Long exerciseID) {
        exerciseService.deleteExercise(principal.getName(), exerciseID);
        return ResponseEntity.noContent().build();
    }

    /* BELOW IS FOR AUTHENTICATED TRAINERS
     * Trainer can create, update or delete exercises
     * */

    @PostMapping("trainers/goals/workouts/exercise")
            public ResponseEntity<ExerciseDto> createExercise(@RequestParam String email,
                                                              @RequestParam Long workoutID,
                                                              @Valid @RequestBody ExerciseDto exerciseDto) {
        return ResponseEntity
                .created(uriBuilder.buildWithEmail(email))
                .body(exerciseService.createExercise(email, workoutID, exerciseDto));
    }

    @GetMapping("trainers/goals/workouts/exercise")
    public ResponseEntity<List<ExerciseDto>> getExercises(@RequestParam String email, @RequestParam Long workoutID) {
        return ResponseEntity.ok().body(exerciseService.getExercises(email, workoutID));
    }

    @PutMapping("trainers/goals/workouts/exercise")
    public ResponseEntity<ExerciseDto> updateExercise(@RequestParam String email,
                                                    @RequestParam Long exerciseID,
                                                    @Valid @RequestBody ExerciseDto exerciseDto) {
        return ResponseEntity.ok().body(exerciseService.updateExercise(email, exerciseID, exerciseDto));
    }

    @DeleteMapping("trainers/goals/workouts/exercise")
    public ResponseEntity<Object> deleteExercise(@RequestParam String email, @RequestParam Long exerciseID) {
        exerciseService.deleteExercise(email, exerciseID);
        return ResponseEntity.noContent().build();
    }
}
