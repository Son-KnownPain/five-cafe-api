package com.fivecafe.body.employee;

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
public class UpdateProOfBillReq {
    @NotNull(message = "Bill ID is required")
    private int billID;
    
    @NotNull(message = "Product ID is required")
    private int productID;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Min quantity is 1")
    private int quantity;
}
