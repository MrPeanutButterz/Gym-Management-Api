package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.EvaluationDto;
import com.novi.gymmanagementapi.exceptions.EmailNotFoundException;
import com.novi.gymmanagementapi.exceptions.RecordNotFoundException;
import com.novi.gymmanagementapi.models.Evaluation;
import com.novi.gymmanagementapi.models.Goal;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.repositories.EvaluationRepository;
import com.novi.gymmanagementapi.repositories.GoalRepository;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {

    private final MemberRepository memberRepository;
    private final EvaluationRepository evaluationRepository;
    private final GoalRepository goalRepository;

    public EvaluationService(MemberRepository memberRepository,
                             EvaluationRepository evaluationRepository,
                             GoalRepository goalRepository) {
        this.memberRepository = memberRepository;
        this.evaluationRepository = evaluationRepository;
        this.goalRepository = goalRepository;
    }


    public EvaluationDto createEvaluation(String email, Long goalID, EvaluationDto evaluationDto) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Optional<Goal> optionalGoal = goalRepository.findById(goalID);
            if (optionalGoal.isPresent()) {
                Goal goal = optionalGoal.get();
                List<Evaluation> evaluationList = goal.getEvaluations();
                evaluationList.add(asMODEL(evaluationDto));
                goal.setEvaluations(evaluationList);
                goalRepository.save(goal);
                return evaluationDto;

            } else {
                throw new RecordNotFoundException(goalID);
            }
        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public List<EvaluationDto> getEvaluationsByGoalID(String email, Long goalID) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Optional<Goal> optionalGoal = goalRepository.findById(goalID);
            if (optionalGoal.isPresent()) {
                List<EvaluationDto> evaluationDtoList = new ArrayList<>();
                for (Evaluation e : optionalGoal.get().getEvaluations()) {
                    evaluationDtoList.add(asDTO(e));
                }
                return evaluationDtoList;

            } else {
                throw new RecordNotFoundException(goalID);
            }
        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public EvaluationDto updateEvaluation(String email, Long evaluationID, EvaluationDto evaluationDto) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Optional<Evaluation> optionalEvaluation = evaluationRepository.findById(evaluationID);
            if (optionalEvaluation.isPresent()) {
                Evaluation evaluation = optionalEvaluation.get();
                evaluation.setDate(evaluationDto.getDate());
                evaluation.setCurrentBodyWeight(evaluationDto.getCurrentBodyWeight());
                evaluation.setDailyCalIntake(evaluationDto.getDailyCalIntake());
                evaluationRepository.save(evaluation);
                return asDTO(evaluation);
            } else {
                throw new RecordNotFoundException(evaluationID);
            }
        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public void deleteEvaluation(String email, Long evaluationID) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Optional<Evaluation> optionalEvaluation = evaluationRepository.findById(evaluationID);
            if (optionalEvaluation.isPresent()) {
                evaluationRepository.delete(optionalEvaluation.get());

            } else {
                throw new RecordNotFoundException(evaluationID);
            }
        } else {
            throw new EmailNotFoundException(email);
        }
    }

    private EvaluationDto asDTO(Evaluation model) {
        EvaluationDto dto = new EvaluationDto();
        dto.setId(model.getId());
        dto.setDate(model.getDate());
        dto.setDailyCalIntake(model.getDailyCalIntake());
        dto.setCurrentBodyWeight(model.getCurrentBodyWeight());
        return dto;
    }

    private Evaluation asMODEL(EvaluationDto dto) {
        Evaluation model = new Evaluation();
        model.setId(dto.getId());
        model.setDate(dto.getDate());
        model.setCurrentBodyWeight(dto.getCurrentBodyWeight());
        model.setDailyCalIntake(dto.getDailyCalIntake());
        return model;
    }
}
