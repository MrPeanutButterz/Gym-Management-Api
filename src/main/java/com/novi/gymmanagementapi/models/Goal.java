package com.novi.gymmanagementapi.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue
    private long id;
    private String description;
    private double currentBodyWeight;
    private double targetBodyWeight;
    private int targetCalorieIntake;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_id", referencedColumnName = "id")
    private List<Workout> workouts;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_id", referencedColumnName = "id")
    private List<Evaluation> evaluations;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_id", referencedColumnName = "id")
    private List<Meal> meals;

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

    public double getCurrentBodyWeight() { return currentBodyWeight; }

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

    public List<Evaluation> getEvaluations() {
        return Objects.requireNonNullElseGet(evaluations, ArrayList::new);
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public List<Workout> getWorkouts() {
        return Objects.requireNonNullElseGet(workouts, ArrayList::new);
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }

    public List<Meal> getMeals() {
        return Objects.requireNonNullElseGet(meals, ArrayList::new);
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
