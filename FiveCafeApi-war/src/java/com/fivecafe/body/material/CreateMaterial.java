package com.fivecafe.body.material;

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
public class CreateMaterial {
    @NotNull
    private int materialCategoryID;
    
    @NotEmpty(message = "Material name cannot be empty")
    private String name;
    
    @NotEmpty(message = "Material unit cannot be empty")
    private String unit;
    
    @NotEmpty(message = "Material image cannot be empty")
    private String image;
}