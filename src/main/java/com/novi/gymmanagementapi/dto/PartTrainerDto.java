package com.novi.gymmanagementapi.dto;

public class PartTrainerDto extends UserDto {

    private double hourlyRate;

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
