
package com.fivecafe.body.bills;

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
public class UpdateBill {
    @NotNull(message = "Bill ID is required")
    private int billID;
    
    @NotNull(message = "Employee ID is required")
    private int employeeID;
    
    @NotNull(message = "Bill status ID is required")
    private int billStatusID;
    
    @NotEmpty(message = "Card code cannot be empty")
    private String cardCode;
    
    
}
