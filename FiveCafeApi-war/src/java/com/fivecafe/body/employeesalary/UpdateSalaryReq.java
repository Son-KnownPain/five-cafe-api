package com.fivecafe.body.employeesalary;

import javax.validation.constraints.NotNull;
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
public class UpdateSalaryReq {
    @NotNull(message = "Employee Salary ID is required")
    private int employeeSalaryID;
    
    @NotNull(message = "Employee ID is required")
    private int employeeID;
}
