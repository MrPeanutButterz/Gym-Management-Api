package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.MealDto;
import com.novi.gymmanagementapi.services.MealService;
import com.novi.gymmanagementapi.utilties.UriBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api")
public class MealController {

    UriBuilder uriBuilder = new UriBuilder();
    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    /* BELOW IS FOR AUTHENTICATED MEMBERS
     * Member can create, update or delete meals
     * */

    @PostMapping("members/goals/meals")
    public ResponseEntity<MealDto> createMeal(Principal principal,
                                              @RequestParam Long goalID,
                                              @Valid @RequestBody MealDto mealDto) {
        return ResponseEntity
                .created(uriBuilder.buildWithEmail(principal.getName()))
                .body(mealService.createMeal(principal.getName(), goalID, mealDto));
    }

    @GetMapping("members/goals/meals")
    public ResponseEntity<List<MealDto>> getMealsByGoalID(Principal principal, @RequestParam Long goalID) {
        return ResponseEntity.ok().body(mealService.getMealsByGoalID(principal.getName(), goalID));
    }

    @PutMapping("members/goals/meals")
    public ResponseEntity<MealDto> updateMeal(Principal principal,
                                              @RequestParam Long mealID,
                                              @Valid @RequestBody MealDto mealDto) {
        return ResponseEntity.ok().body(mealService.updateMeal(principal.getName(), mealID, mealDto));
    }

    @DeleteMapping("members/goals/meals")
    public ResponseEntity<Object> deleteMeal(Principal principal, @RequestParam Long mealID) {
        mealService.deleteMeal(principal.getName(), mealID);
        return ResponseEntity.noContent().build();
    }

    /* BELOW IS FOR AUTHENTICATED TRAINERS
     * Trainer can create, update or delete meals for members
     * */

    @PostMapping("trainers/goals/meals")
    public ResponseEntity<MealDto> createMeal(String email,
                                              @RequestParam Long goalID,
                                              @Valid @RequestBody MealDto mealDto) {
        return ResponseEntity
                .created(uriBuilder.buildWithEmail(email))
                .body(mealService.createMeal(email, goalID, mealDto));
    }

    @GetMapping("trainers/goals/meals")
    public ResponseEntity<List<MealDto>> getMealByID(String email, @RequestParam Long goalID) {
        return ResponseEntity.ok().body(mealService.getMealsByGoalID(email, goalID));
    }

    @PutMapping("trainers/goals/meals")
    public ResponseEntity<MealDto> updateMeal(String email,
                                              @RequestParam Long mealID,
                                              @Valid @RequestBody MealDto mealDto) {
        return ResponseEntity.ok().body(mealService.updateMeal(email, mealID, mealDto));
    }

    @DeleteMapping("trainers/goals/meals")
    public ResponseEntity<Object> deleteMeal(String email, @RequestParam Long mealID) {
        mealService.deleteMeal(email, mealID);
        return ResponseEntity.noContent().build();
    }
}
