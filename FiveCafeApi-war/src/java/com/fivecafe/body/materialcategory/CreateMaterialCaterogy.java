package com.fivecafe.body.materialcategory;

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
public class CreateMaterialCaterogy {
    @NotEmpty(message = "Material category name cannot be empty")
    private String name;
    
    @NotEmpty(message = "Material category description cannot be empty")
    private String description;
}
