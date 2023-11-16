package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.ExerciseDto;
import com.novi.gymmanagementapi.exceptions.EmailNotFoundException;
import com.novi.gymmanagementapi.exceptions.RecordNotFoundException;
import com.novi.gymmanagementapi.models.Exercise;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.models.Workout;
import com.novi.gymmanagementapi.repositories.ExerciseRepository;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import com.novi.gymmanagementapi.repositories.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final MemberRepository memberRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    public ExerciseService(MemberRepository memberRepository,
                           WorkoutRepository workoutRepository,
                           ExerciseRepository exerciseRepository) {
        this.memberRepository = memberRepository;
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public ExerciseDto createExercise(String email, Long workoutID, ExerciseDto exerciseDto) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Optional<Workout> optionalWorkout = workoutRepository.findById(workoutID);
            if (optionalWorkout.isPresent()) {
                Workout workout = optionalWorkout.get();
                List<Exercise> exerciseList = workout.getExercises();
                exerciseList.add(asMODEL(exerciseDto));
                workout.setExercises(exerciseList);
                workoutRepository.save(workout);
                return exerciseDto;

            } else {
                throw new RecordNotFoundException(workoutID);
            }
        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public List<ExerciseDto> getExercises(String email, Long workoutID) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Optional<Workout> optionalWorkout = workoutRepository.findById(workoutID);
            if (optionalWorkout.isPresent()) {
                List<ExerciseDto> exerciseDtoList = new ArrayList<>();
                for (Exercise e : optionalWorkout.get().getExercises()) {
                    exerciseDtoList.add(asDTO(e));
                }
                return exerciseDtoList;
            } else {
                throw new RecordNotFoundException();
            }
        } else {
            throw new RecordNotFoundException();
        }
    }

    public ExerciseDto updateExercise(String email, Long exerciseID, ExerciseDto exerciseDto) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Optional<Exercise> optionalExercise = exerciseRepository.findById(exerciseID);
            if (optionalExercise.isPresent()) {
                Exercise exercise = optionalExercise.get();
                exercise.setName(exerciseDto.getName());
                exercise.setWeight(exerciseDto.getWeight());
                exercise.setReps(exerciseDto.getReps());
                exercise.setSets(exerciseDto.getSets());
                exerciseRepository.save(exercise);
                return asDTO(exercise);

            } else {
                throw new RecordNotFoundException(exerciseID);
            }
        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public void deleteExercise(String email, Long exerciseID) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
        Optional<Exercise> optionalExercise = exerciseRepository.findById(exerciseID);
            if (optionalExercise.isPresent()) {
                exerciseRepository.delete(optionalExercise.get());

            } else {
                throw new RecordNotFoundException(exerciseID);
            }
        } else {
            throw new EmailNotFoundException(email);
        }
    }

    private Exercise asMODEL(ExerciseDto dto) {
        Exercise model = new Exercise();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setWeight(dto.getWeight());
        model.setReps(dto.getReps());
        model.setSets(dto.getSets());
        return model;
    }

    private ExerciseDto asDTO(Exercise model) {
        ExerciseDto dto = new ExerciseDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setWeight(model.getWeight());
        dto.setReps(model.getReps());
        dto.setSets(model.getSets());
        return dto;
    }
}
