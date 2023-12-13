package com.fivecafe.body.employeesalary;

import java.util.List;
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
public class EmployeeSalariesResponse {
    private int employeeSalaryID;
    private String date;
    // Employee
    private int employeeID;
    private String employeeName;
    // Detail
    private List<EmployeeSalaryDetailResponse> details;
}
