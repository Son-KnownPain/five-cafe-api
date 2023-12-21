package com.fivecafe.body.billstatus;

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
public class CUBillStatusReq {
    @NotNull(message = "Bill Status ID is required")
    @Min(value = 1, message = "Min bill status id value is 1")
    private int billStatusID;
    @NotEmpty(message = "Bill Status Value cannot be empty")
    private String billStatusValue;
    @NotNull(message = "To check is required")
    private boolean toCheck;
}
