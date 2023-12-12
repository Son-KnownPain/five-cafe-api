package com.fivecafe.body.billstatus;

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
public class UpdateAndDeleteBillStatus {
    @NotNull(message = "Material ID is required")
    private int billStatusID;
    
    @NotEmpty(message = "Bill status cannot be empty")
    private String billStatusValue;
}
