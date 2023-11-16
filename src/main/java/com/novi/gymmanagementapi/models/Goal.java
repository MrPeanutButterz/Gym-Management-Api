package com.novi.gymmanagementapi.models;

import jakarta.persistence.*;
import org.hibernate.jdbc.Work;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private double currentBodyWeight;
    private double targetBodyWeight;
    private int targetCalorieIntake;
    private LocalDate startDate;
    private LocalDate endDate;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_id", referencedColumnName = "id")
    private List<Evaluation> evaluations;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_id", referencedColumnName = "id")
    private List<Meal> meals;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "workout_id", referencedColumnName = "id")
    private List<Workout> workouts;

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
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) { this.evaluations = evaluations; }

    public List<Meal> getMeals() { return meals; }

    public void setMeals(List<Meal> meals) { this.meals = meals; }

    public List<Workout> getWorkouts() { return workouts; }

    public void setWorkouts(List<Workout> workouts) { this.workouts = workouts; }

    public List<Long> getEvaluationIDs() {
        // transforms a list of evaluations to a list of evaluation IDs
        List<Long> IDs = new ArrayList<>();
        for (Evaluation e : this.evaluations) {
            IDs.add(e.getId());
        }
        return IDs;
    }

    public List<Long> getMealIDs() {
        // transforms a list of workouts to a list of workout IDs
        List<Long> IDs = new ArrayList<>();
        for (Meal m : this.meals) {
            IDs.add(m.getId());
        }
        return IDs;
    }

    public List<Long> getWorkoutIDs() {
        // transforms a list of workouts to a list of workout IDs
        List<Long> IDs = new ArrayList<>();
        for (Workout w : this.workouts) {
            IDs.add(w.getId());
        }
        return IDs;
    }
}
