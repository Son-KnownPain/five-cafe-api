package com.fivecafe.body.shift;

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
public class UpdateAndDeleteShift {
    @NotNull(message = "Shift ID is required")
    private int shiftID;
    
    @NotEmpty(message = "Shift name cannot be empty")
    private String name;
    
    @NotNull(message = "Salary is required")   
    private double salary;
    
    @NotNull(message = "Time from is required")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", message = "Invalid time format")
    private String timeFrom;
    
    @NotNull(message = "Time to is required")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", message = "Invalid time format")
    private String timeTo;
    
}
