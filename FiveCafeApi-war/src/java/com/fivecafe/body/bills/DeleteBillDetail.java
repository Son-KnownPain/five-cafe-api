package com.fivecafe.body.bills;

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
public class DeleteBillDetail {
    @NotNull(message = "Bill ID is required")
    private int billID;
    
    @NotNull(message = "Product ID is required")
    private int productID;
}
