package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.GoalDto;
import com.novi.gymmanagementapi.services.GoalService;
import com.novi.gymmanagementapi.utilties.UriBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api")
public class GoalController {

    UriBuilder uriBuilder = new UriBuilder();
    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    /* BELOW IS FOR AUTHENTICATED MEMBERS
     * Member can create, update or delete goals
     * */

    @PostMapping("members/goals")
    public ResponseEntity<GoalDto> createGoal(Principal principal, @Valid @RequestBody GoalDto goalDto) {
        GoalDto dto = goalService.createGoal(principal.getName(), goalDto);
        return ResponseEntity.created(uriBuilder.buildWithEmail(principal.getName())).body(dto);
    }

    @GetMapping("members/goals")
    public ResponseEntity<List<GoalDto>> getGoals(Principal principal) {
        return ResponseEntity.ok().body(goalService.getGoals(principal.getName()));
    }

    @PutMapping("members/goals")
    public ResponseEntity<GoalDto> updateGoal(Principal principal, @RequestParam Long goalID, @RequestBody GoalDto goalDto) {
        return ResponseEntity.ok().body(goalService.updateGoal(principal.getName(), goalID, goalDto));
    }

    @DeleteMapping("members/goals")
    public ResponseEntity<Object> deleteGoal(Principal principal, @RequestParam Long goalID) {
        goalService.deleteGoal(principal.getName(), goalID);
        return ResponseEntity.noContent().build();
    }

    /* BELOW IS FOR AUTHENTICATED TRAINER
     * Trainers can create, update or delete goals for members
     * */

    @PostMapping("trainers/goals")
    public ResponseEntity<GoalDto> createGoal(@RequestParam String email, @Valid @RequestBody GoalDto goalDto) {
        goalService.createGoal(email, goalDto);
        return ResponseEntity.created(uriBuilder.buildWithEmail(email)).build();
    }

    @GetMapping("trainers/goals")
    public ResponseEntity<List<GoalDto>> getGoals(@RequestParam String email) {
        return ResponseEntity.ok().body(goalService.getGoals(email));
    }

    @PutMapping("trainers/goals")
    public ResponseEntity<GoalDto> updateGoal(@RequestParam String email, @RequestParam Long goalID, @RequestBody GoalDto goalDto) {
        return ResponseEntity.ok().body(goalService.updateGoal(email, goalID, goalDto));
    }

    @DeleteMapping("trainers/goals")
    public ResponseEntity<Object> deleteGoal(@RequestParam String email, @RequestParam Long goalID) {
        goalService.deleteGoal(email, goalID);
        return ResponseEntity.noContent().build();
    }
}
