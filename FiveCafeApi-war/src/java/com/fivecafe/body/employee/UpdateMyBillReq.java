package com.fivecafe.body.employee;

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
public class UpdateMyBillReq {
    @NotNull(message = "Bill ID is required")
    private int billID;
    
    @NotNull(message = "Bill status ID is required")
    private int billStatusID;
    
    @NotEmpty(message = "Card code is required")
    private String cardCode;
}
