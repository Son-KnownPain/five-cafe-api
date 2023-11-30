package com.fivecafe.body.employeetimekeeping;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEmployeeTimeKeeping {
    @NotNull(message = "Employee ID is required")
    private int employeeID;
    
    @NotNull(message = "Shift ID is required")
    private int shiftID;
    
    @NotNull(message = "Date is required")
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Invalid date format(dd/MM/yyyy), example: 01/10/2023")
    private String date;
    
    @NotNull(message = "Salary is required") 
    private Double salary;
    
    @NotNull(message = "Is pay is required") 
    private Boolean isPaid;
   
}
