package com.fivecafe.body.material;

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
public class UpdateAndDeleteMaterial {
    @NotNull(message = "Material ID is required")
    private int materialID;
    
    @NotNull(message = "Material category ID is required")
    private int materialCategoryID;
    
    @NotEmpty(message = "Material name cannot be empty")
    private String name;
    
    @NotEmpty(message = "Material unit cannot be empty")
    private String unit;
    
    @NotNull(message = "Quantity in stock is required")
    @Min(value = 0, message = "Min quantity in stock value is 0")
    private int quantityInStock;
    
//    @NotEmpty(message = "Material image cannot be empty")
    private String image;
}
