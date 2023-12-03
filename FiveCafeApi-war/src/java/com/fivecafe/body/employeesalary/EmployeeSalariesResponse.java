package com.fivecafe.body.employeesalary;

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
    
    private int employeeID;
    
    private String date;
}
