
package com.fivecafe.body.employeesalary;

import javax.validation.constraints.Min;
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
public class UpdateDetailItemReq {
    @NotNull(message = "Employee ID is required")
    private int employeeSalaryID;
    
    @NotNull(message = "Employee timekeeping ID is required")
    private int etkID;

    @NotNull(message = "Bonus is required")
    @Min(value = 0, message = "Min bonus value is 0")
    private int bonus;

    @NotNull(message = "Deduction is required")
    @Min(value = 0, message = "Min deduction value is 0")
    private double deduction;
}
