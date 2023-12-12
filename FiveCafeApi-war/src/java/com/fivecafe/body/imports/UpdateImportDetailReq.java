package com.fivecafe.body.imports;

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
public class UpdateImportDetailReq {
    @NotNull(message = "Import ID is required")
    private int importID;
    
    @NotNull(message = "Material ID is required")
    private int materialID;
    
    @NotNull(message = "Supplier ID is required")
    private int supplierID;

    @NotNull(message = "Unit price is required")
    @Min(value = 0, message = "Min value is 0")
    private double unitPrice;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Min quantity is 1")
    private int quantity;
}
