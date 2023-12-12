package com.fivecafe.body.employeesalary;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEmployeeSalaries {
    @NotNull(message = "Employee ID is required")
    private int employeeID;
    
    @NotEmpty(message = "Date is required")
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Invalid date format(dd/MM/yyyy), example: 01/10/2023")
    private String date;
     
    @NotEmpty(message = "Time keeping is required")
    private List<Integer> timeKeepingIDs;
}
