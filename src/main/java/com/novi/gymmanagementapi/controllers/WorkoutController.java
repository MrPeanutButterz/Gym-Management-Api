package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.WorkoutDto;
import com.novi.gymmanagementapi.services.WorkoutService;
import com.novi.gymmanagementapi.utilties.UriBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api")
public class WorkoutController {

    UriBuilder uriBuilder = new UriBuilder();
    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    /* BELOW IS FOR AUTHENTICATED MEMBERS
     * Member can create, update or delete goals
     * */

    @PostMapping("members/goals/workouts")
    public ResponseEntity<WorkoutDto> createWorkout(Principal principal,
                                                    @RequestParam Long goalID,
                                                    @Valid @RequestBody WorkoutDto workoutDto) {
        return ResponseEntity
                .created(uriBuilder.buildWithEmail(principal.getName()))
                .body(workoutService.createWorkout(principal.getName(), goalID, workoutDto));
    }

    @GetMapping("members/goals/workouts")
    public ResponseEntity<List<WorkoutDto>> getWorkouts(Principal principal, @RequestParam Long goalID) {
        return ResponseEntity.ok().body(workoutService.getWorkouts(principal.getName(), goalID));
    }

    @PutMapping("members/goals/workouts")
    public ResponseEntity<WorkoutDto> updateWorkout(Principal principal,
                                                    @RequestParam Long workoutID,
                                                    @Valid @RequestBody WorkoutDto workoutDto) {
        return ResponseEntity.ok().body(workoutService.updateWorkout(principal.getName(), workoutID, workoutDto));
    }

    @DeleteMapping("members/goals/workouts")
    public ResponseEntity<Object> deleteWorkout(Principal principal, @RequestParam Long workoutID) {
        workoutService.deleteWorkout(principal.getName(), workoutID);
        return ResponseEntity.noContent().build();
    }

    /* BELOW IS FOR AUTHENTICATED TRAINER
     * Trainers can create, update or delete goals for members
     * */

    @PostMapping("trainers/goals/workouts")
    public ResponseEntity<WorkoutDto> createWorkout(@RequestParam String email,
                                                    @RequestParam Long goalID,
                                                    @Valid @RequestBody WorkoutDto workoutDto) {
        return ResponseEntity
                .created(uriBuilder.buildWithEmail(email))
                .body(workoutService.createWorkout(email, goalID, workoutDto));
    }

    @GetMapping("trainers/goals/workouts")
    public ResponseEntity<List<WorkoutDto>> getWorkouts(@RequestParam String email, @RequestParam Long goalID) {
        return ResponseEntity.ok().body(workoutService.getWorkouts(email, goalID));
    }

    @PutMapping("trainers/goals/workouts")
    public ResponseEntity<WorkoutDto> updateWorkout(@RequestParam String email,
                                                    @RequestParam Long workoutID,
                                                    @Valid @RequestBody WorkoutDto workoutDto) {
        return ResponseEntity.ok().body(workoutService.updateWorkout(email, workoutID, workoutDto));
    }

    @DeleteMapping("trainers/goals/workouts")
    public ResponseEntity<Object> deleteWorkout(@RequestParam String email, @RequestParam Long workoutID) {
        workoutService.deleteWorkout(email, workoutID);
        return ResponseEntity.noContent().build();
    }
}
