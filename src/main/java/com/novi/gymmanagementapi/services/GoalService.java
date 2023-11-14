package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.GoalDto;
import com.novi.gymmanagementapi.exceptions.EmailNotFoundException;
import com.novi.gymmanagementapi.models.Goal;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.repositories.GoalRepository;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final MemberRepository memberRepository;

    public GoalService(GoalRepository goalRepository, MemberRepository memberRepository) {
        this.goalRepository = goalRepository;
        this.memberRepository = memberRepository;
    }

    public GoalDto createGoal(String email, GoalDto goalDto) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            List<Goal> goalList = member.getGoals();
            goalList.add(asModel(goalDto));
            member.setGoals(goalList);
            memberRepository.save(member);
            return goalDto;

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public List<GoalDto> getGoals(String email) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            List<Goal> goalList = optionalMember.get().getGoals();
            List<GoalDto> goalDtoList = new ArrayList<>();
            for (Goal g : goalList) {
                GoalDto goalDto = asDTO(g);
                goalDtoList.add(goalDto);
            }
            return goalDtoList;

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public GoalDto updateGoal(String email, Long goalID, GoalDto goalDto) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        Optional<Goal> optionalGoal = goalRepository.findById(goalID);
        if (optionalMember.isPresent() && optionalGoal.isPresent()) {
            Goal goal = optionalGoal.get();
            goal.setDescription(goalDto.getDescription());
            goal.setCurrentBodyWeight(goalDto.getCurrentBodyWeight());
            goal.setTargetBodyWeight(goalDto.getTargetBodyWeight());
            goal.setTargetCalorieIntake(goalDto.getTargetCalorieIntake());
            goal.setStartDate(goalDto.getStartDate());
            goal.setEndDate(goalDto.getEndDate());
            goalRepository.save(goal);
            return asDTO(goal);

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public void deleteGoal(String email, Long goalID) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        Optional<Goal> optionalGoal = goalRepository.findById(goalID);
        if (optionalMember.isPresent() && optionalGoal.isPresent()) {
            goalRepository.delete(optionalGoal.get());

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public GoalDto asDTO(Goal model) {
        GoalDto dto = new GoalDto();
        dto.setId(model.getId());
        dto.setCurrentBodyWeight(model.getCurrentBodyWeight());
        dto.setDescription(model.getDescription());
        dto.setTargetBodyWeight(model.getTargetBodyWeight());
        dto.setTargetCalorieIntake(model.getTargetCalorieIntake());
        dto.setStartDate(model.getStartDate());
        dto.setEndDate(model.getEndDate());
        return dto;
    }

    public Goal asModel(GoalDto dto) {
        Goal goal = new Goal();
        goal.setId(dto.getId());
        goal.setDescription(dto.getDescription());
        goal.setCurrentBodyWeight(dto.getCurrentBodyWeight());
        goal.setTargetBodyWeight(dto.getTargetBodyWeight());
        goal.setTargetCalorieIntake(dto.getTargetCalorieIntake());
        goal.setStartDate(dto.getStartDate());
        goal.setEndDate(dto.getEndDate());
        return goal;
    }
}
