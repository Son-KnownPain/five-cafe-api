package com.fivecafe.body.mattopro;

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
public class UpdateAndDeleteMatToPro {
    @NotNull(message = "Material ID is required")
    private int materialID;
    
    @NotNull(message = "Product ID is required")
    private int productID;
    
    @NotEmpty(message = "Description cannot be empty")
    private String description;
}
