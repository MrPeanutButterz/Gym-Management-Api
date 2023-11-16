package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.WorkoutDto;
import com.novi.gymmanagementapi.exceptions.RecordNotFoundException;
import com.novi.gymmanagementapi.models.Goal;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.models.Workout;
import com.novi.gymmanagementapi.repositories.GoalRepository;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import com.novi.gymmanagementapi.repositories.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {

    private final MemberRepository memberRepository;
    private final WorkoutRepository workoutRepository;
    private final GoalRepository goalRepository;

    public WorkoutService(MemberRepository memberRepository, WorkoutRepository workoutRepository, GoalRepository goalRepository) {
        this.memberRepository = memberRepository;
        this.workoutRepository = workoutRepository;
        this.goalRepository = goalRepository;
    }

    public WorkoutDto createWorkout(String email, Long goalID, WorkoutDto workoutDto) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        Optional<Goal> optionalGoal = goalRepository.findById(goalID);
        if (optionalMember.isPresent() && optionalGoal.isPresent()) {
            Goal goal = optionalGoal.get();
            List<Workout> workoutList = goal.getWorkouts();
            workoutList.add(asMODEL(workoutDto));
            goal.setWorkouts(workoutList);
            goalRepository.save(goal);
            return workoutDto;

        } else {
            throw new RecordNotFoundException();
        }
    }

    public List<WorkoutDto> getWorkouts(String email, Long goalID) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Optional<Goal> optionalGoal = goalRepository.findById(goalID);
            if (optionalGoal.isPresent()) {
                List<WorkoutDto> workoutDtoList = new ArrayList<>();
                for (Workout w : optionalGoal.get().getWorkouts()) {
                    workoutDtoList.add(asDTO(w));
                }
                return workoutDtoList;
            } else {
                throw new RecordNotFoundException();
            }
        } else {
            throw new RecordNotFoundException();
        }
    }

    public WorkoutDto updateWorkout(String email, Long workoutID, WorkoutDto workoutDto) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        Optional<Workout> optionalWorkout = workoutRepository.findById(workoutID);
        if (optionalMember.isPresent() && optionalWorkout.isPresent()) {
            Workout workout = optionalWorkout.get();
            workout.setDate(workoutDto.getDate());
            workout.setName(workoutDto.getName());
            workoutRepository.save(workout);
            return asDTO(workout);

        } else {
            throw new RecordNotFoundException();
        }
    }

    public void deleteWorkout(String email, Long workoutID) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        Optional<Workout> optionalWorkout = workoutRepository.findById(workoutID);
        if (optionalMember.isPresent() && optionalWorkout.isPresent()) {
            workoutRepository.delete(optionalWorkout.get());
        } else {
            throw new RecordNotFoundException();
        }
    }

    private Workout asMODEL(WorkoutDto dto) {
        Workout model = new Workout();
        model.setDate(dto.getDate());
        model.setId(dto.getId());
        model.setName(dto.getName());
        return model;
    }

    private WorkoutDto asDTO(Workout model) {
        WorkoutDto dto = new WorkoutDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setDate(model.getDate());
        dto.setExerciseIDs(model.getExerciseIDs());
        return dto;
    }
}
