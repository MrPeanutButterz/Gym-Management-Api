package com.novi.gymmanagementapi.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "evaluations")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date date;
    private double weight;
    private int dailyCalIntake;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getDailyCalIntake() {
        return dailyCalIntake;
    }

    public void setDailyCalIntake(int dailyCalIntake) {
        this.dailyCalIntake = dailyCalIntake;
    }
}
