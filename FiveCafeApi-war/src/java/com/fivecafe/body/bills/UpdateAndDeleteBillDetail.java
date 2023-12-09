package com.fivecafe.body.bills;

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
public class UpdateAndDeleteBillDetail {
    
    @NotNull(message = "Bill ID is required")
    private int billID;
    
    @NotNull(message = "Employee ID is required")
    private int employeeID;
    
    @NotNull(message = "Bill status ID is required")
    private int billStatusID;
    
    @NotNull(message = "Create date is required")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])$", message = "Invalid time from format(HH:mm:ss), example: 6:00:00")
    private String createDate;
    
    @NotEmpty(message = "Card code cannot be empty")   
    private String cardCode;
}
