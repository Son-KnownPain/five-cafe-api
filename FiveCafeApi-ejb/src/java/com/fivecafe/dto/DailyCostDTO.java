package com.fivecafe.dto;

import java.util.Date;

public class DailyCostDTO {
    private double cost;
    private Date date;

    public DailyCostDTO() {
    }

    public DailyCostDTO(double cost, Date date) {
        this.cost = cost;
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
