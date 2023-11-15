package com.novi.gymmanagementapi.dto;

import com.novi.gymmanagementapi.models.Evaluation;

import java.time.LocalDate;
import java.util.List;

public class GoalDto {

    private long id;
    private String description;
    private double currentBodyWeight;
    private double targetBodyWeight;
    private int targetCalorieIntake;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> evaluationIDs;
    private List<Long> mealIDs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCurrentBodyWeight() {
        return currentBodyWeight;
    }

    public void setCurrentBodyWeight(double currentBodyWeight) {
        this.currentBodyWeight = currentBodyWeight;
    }

    public double getTargetBodyWeight() {
        return targetBodyWeight;
    }

    public void setTargetBodyWeight(double targetBodyWeight) {
        this.targetBodyWeight = targetBodyWeight;
    }

    public int getTargetCalorieIntake() {
        return targetCalorieIntake;
    }

    public void setTargetCalorieIntake(int targetCalorieIntake) {
        this.targetCalorieIntake = targetCalorieIntake;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Long> getEvaluationIDs() {
        return evaluationIDs;
    }

    public void setEvaluationIDs(List<Long> evaluationIDs) {
        this.evaluationIDs = evaluationIDs;
    }

    public List<Long> getMealIDs() {
        return mealIDs;
    }

    public void setMealIDs(List<Long> mealIDs) {
        this.mealIDs = mealIDs;
    }
}
