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
public class EmployeeSalaryDetailResponse {
    // Timekeeping
    private int employeeTimekeepingID;
    private String shiftName;
    private double salary;
    private String timeKeepingDate;

    //  Emp salary detail
    private int bonus;
    private double deduction;
}
