package com.fivecafe.body.employeetimekeeping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeTimeKeepingResponse {
    private int timeKeepingID;
    private int employeeID;
    private int shiftID;
    private String date;
    private Double salary;
    private Boolean isPaid;
}