package com.novi.gymmanagementapi.dto;

import com.novi.gymmanagementapi.models.Workout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDto {

    private Long id;
    private String name;
    private LocalDate date;
    private List<Long> exerciseIDs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Long> getExerciseIDs() {
        return exerciseIDs;
    }

    public void setExerciseIDs(List<Long> exerciseIDs) {
        this.exerciseIDs = exerciseIDs;
    }
}
