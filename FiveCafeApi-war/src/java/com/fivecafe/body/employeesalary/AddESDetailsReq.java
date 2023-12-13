package com.fivecafe.body.employeesalary;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
public class AddESDetailsReq {
    @NotNull(message = "Employee Salary ID is required")
    private int employeeSalaryID;
    
    @Valid
    @NotEmpty(message = "Time keepings cannot be empty")
    private List<AddEmployeeSalaryDetailReq> details;
    
    // To reject value of binding result
    private Object forError;
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AddEmployeeSalaryDetailReq {
        @NotNull(message = "Employee timekeeping ID is required")
        private int etkID;
        
        @NotNull(message = "Bonus is required")
        @Min(value = 0, message = "Min bonus value is 0")
        private int bonus;
        
        @NotNull(message = "Deduction is required")
        @Min(value = 0, message = "Min deduction value is 0")
        private double deduction;
    }
}
