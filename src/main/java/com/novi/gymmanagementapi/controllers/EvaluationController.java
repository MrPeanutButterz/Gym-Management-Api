package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.EvaluationDto;
import com.novi.gymmanagementapi.services.EvaluationService;
import com.novi.gymmanagementapi.utilties.UriBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api")
public class EvaluationController {

    UriBuilder uriBuilder = new UriBuilder();
    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    /* BELOW IS FOR AUTHENTICATED MEMBERS
     * Member can create, update or delete evaluations
     * */

    @PostMapping("members/goals/evaluations")
    public ResponseEntity<EvaluationDto> createEvaluation(Principal principal,
                                                          @RequestParam Long goalID,
                                                          @Valid @RequestBody EvaluationDto evaluationDto) {
        return ResponseEntity
                .created(uriBuilder.buildWithEmail(principal.getName()))
                .body(evaluationService.createEvaluation(principal.getName(), goalID, evaluationDto));
    }

    @GetMapping("members/goals/evaluations")
    public ResponseEntity<List<EvaluationDto>> getEvaluationByID(Principal principal, @RequestBody List<Long> evaluationIDs) {
        return ResponseEntity.ok().body(evaluationService.getEvaluationsByID(principal.getName(), evaluationIDs));
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

    /* BELOW IS FOR AUTHENTICATED TRAINERS
     * Trainer can create, update or delete evaluations for members
     * todo has to be tested yet! */

    @PostMapping("trainers/goals/evaluations")
    public ResponseEntity<EvaluationDto> createEvaluation(String email,
                                                          @RequestParam Long goalID,
                                                          @Valid @RequestBody EvaluationDto evaluationDto) {
        return ResponseEntity
                .created(uriBuilder.buildWithEmail(email))
                .body(evaluationService.createEvaluation(email, goalID, evaluationDto));
    }

    @GetMapping("trainers/goals/evaluations")
    public ResponseEntity<List<EvaluationDto>> getEvaluationByID(String email, @RequestBody List<Long> evaluationIDs) {
        return ResponseEntity.ok().body(evaluationService.getEvaluationsByID(email, evaluationIDs));
    }

    @PutMapping("trainers/goals/evaluations")
    public ResponseEntity<EvaluationDto> updateEvaluation(String email,
                                                          @RequestParam Long evaluationID,
                                                          @Valid @RequestBody EvaluationDto evaluationDto) {
        return ResponseEntity.ok().body(evaluationService.updateEvaluation(email, evaluationID, evaluationDto));
    }

    @DeleteMapping("trainers/goals/evaluations")
    public ResponseEntity<Object> deleteEvaluation(String email, @RequestParam Long evaluationID) {
        evaluationService.deleteEvaluation(email, evaluationID);
        return ResponseEntity.noContent().build();
    }
}
