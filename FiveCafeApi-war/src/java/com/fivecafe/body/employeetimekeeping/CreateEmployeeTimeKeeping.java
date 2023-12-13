package com.fivecafe.body.employeetimekeeping;

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
public class CreateEmployeeTimeKeeping {
    @NotNull(message = "Employee ID is required")
    private int employeeID;
    
    @NotNull(message = "Shift ID is required")
    private int shiftID;
}
