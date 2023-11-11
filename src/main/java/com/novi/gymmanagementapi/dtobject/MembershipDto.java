package com.novi.gymmanagementapi.dtobject;

public class MembershipDto {

    private long id;
    private String name;
    private int contractLengthInWeek;
    private double pricePerMonth;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContractLengthInWeek() {
        return contractLengthInWeek;
    }

    public void setContractLengthInWeek(int contractLengthInWeek) {
        this.contractLengthInWeek = contractLengthInWeek;
    }

    public double getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(double pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }
}
