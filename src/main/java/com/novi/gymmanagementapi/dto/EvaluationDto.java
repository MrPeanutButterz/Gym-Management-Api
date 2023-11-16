package com.novi.gymmanagementapi.dto;

import java.time.LocalDate;

public class EvaluationDto {

    private long id;
    private LocalDate date;
    private double currentBodyWeight;
    private int dailyCalIntake;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getCurrentBodyWeight() {
        return currentBodyWeight;
    }

    public void setCurrentBodyWeight(double currentBodyWeight) {
        this.currentBodyWeight = currentBodyWeight;
    }

    public int getDailyCalIntake() {
        return dailyCalIntake;
    }

    public void setDailyCalIntake(int dailyCalIntake) {
        this.dailyCalIntake = dailyCalIntake;
    }
}
