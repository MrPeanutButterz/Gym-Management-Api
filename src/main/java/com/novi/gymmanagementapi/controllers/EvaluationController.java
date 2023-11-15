package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.EvaluationDto;
import com.novi.gymmanagementapi.services.EvaluationService;
import com.novi.gymmanagementapi.utilties.UriBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api")
public class EvaluationController {

    UriBuilder uriBuilder = new UriBuilder();
    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping("members/goals/evaluations")
    public ResponseEntity<EvaluationDto> createEvaluation(Principal principal,
                                                          @RequestParam Long goalID,
                                                          @Valid @RequestBody EvaluationDto evaluationDto) {
        return ResponseEntity
                .created(uriBuilder.buildWithEmail(principal.getName()))
                .body(evaluationService.createEvaluation(principal.getName(), goalID, evaluationDto));
    }

    @PutMapping("members/goals/evaluations")
    public ResponseEntity<EvaluationDto> updateEvaluation(Principal principal,
                                                          @RequestParam Long evaluationID,
                                                          @Valid @RequestBody EvaluationDto evaluationDto) {
        return ResponseEntity.ok().body(evaluationService.updateEvaluation(principal.getName(), evaluationID, evaluationDto));
    }

    @DeleteMapping("members/goals/evaluations")
    public ResponseEntity<Object> deleteEvaluation(Principal principal, @RequestParam Long evaluationID) {
        evaluationService.deleteEvaluation(principal.getName(), evaluationID);
        return ResponseEntity.noContent().build();
    }
}
