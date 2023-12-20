package com.fivecafe.dto;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class DailyRevenueDTO {
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    private double revenue;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public DailyRevenueDTO() {
    }

    public DailyRevenueDTO(double revenue, Date date) {
        this.date = date;
        this.revenue = revenue;
    }
}
