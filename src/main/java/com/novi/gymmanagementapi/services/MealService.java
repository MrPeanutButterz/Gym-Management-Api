package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.MealDto;
import com.novi.gymmanagementapi.exceptions.EmailNotFoundException;
import com.novi.gymmanagementapi.exceptions.RecordNotFoundException;
import com.novi.gymmanagementapi.models.Goal;
import com.novi.gymmanagementapi.models.Meal;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.repositories.GoalRepository;
import com.novi.gymmanagementapi.repositories.MealRepository;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final MemberRepository memberRepository;
    private final GoalRepository goalRepository;

    public MealService(MealRepository mealRepository,
                       MemberRepository memberRepository,
                       GoalRepository goalRepository) {
        this.mealRepository = mealRepository;
        this.memberRepository = memberRepository;
        this.goalRepository = goalRepository;
    }


    public MealDto createMeal(String email, Long goalID, MealDto mealDto) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Optional<Goal> optionalGoal = goalRepository.findById(goalID);
            if (optionalGoal.isPresent()) {
                Goal goal = optionalGoal.get();
                List<Meal> mealList = goal.getMeals();
                mealList.add(asMODEL(mealDto));
                goal.setMeals(mealList);
                goalRepository.save(goal);
                return mealDto;
            } else {
                throw new RecordNotFoundException(goalID);
            }
        } else {
            throw new EmailNotFoundException(email);
        }
    }


    public List<MealDto> getMealsByGoalID(String email, Long goalID) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Optional<Goal> optionalGoal = goalRepository.findById(goalID);
            if (optionalGoal.isPresent()) {
                List<MealDto> mealDtoList = new ArrayList<>();
                for (Meal m : optionalGoal.get().getMeals()) {
                    mealDtoList.add(asDTO(m));
                }
                return mealDtoList;

            } else {
                throw new RecordNotFoundException(goalID);
            }
        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public MealDto updateMeal(String email, Long mealID, MealDto mealDto) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Optional<Meal> optionalMeal = mealRepository.findById(mealID);
            if (optionalMeal.isPresent()) {
                Meal meal = optionalMeal.get();
                meal.setDate(mealDto.getDate());
                meal.setCalories(mealDto.getCalories());
                meal.setName(mealDto.getName());
                mealRepository.save(meal);
                return asDTO(meal);

            } else {
                throw new RecordNotFoundException(mealID);
            }
        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public void deleteMeal(String email, Long mealID) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Optional<Meal> optionalMeal = mealRepository.findById(mealID);
            if (optionalMeal.isPresent()) {
                mealRepository.delete(optionalMeal.get());

            } else {
                throw new RecordNotFoundException(mealID);
            }
        } else {
            throw new EmailNotFoundException(email);
        }
    }

    private MealDto asDTO(Meal model) {
        MealDto dto = new MealDto();
        dto.setId(model.getId());
        dto.setDate(model.getDate());
        dto.setName(model.getName());
        dto.setCalories(model.getCalories());
        return dto;
    }

    private Meal asMODEL(MealDto dto) {
        Meal model = new Meal();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setDate(dto.getDate());
        model.setCalories(dto.getCalories());
        return model;
    }
}
