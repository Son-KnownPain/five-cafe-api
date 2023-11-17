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
    @NotNull
    private int materialID;
    
    @NotNull
    private int materialCategoryID;
    
    @NotEmpty(message = "Material name cannot be empty")
    @NotNull
    private String name;
    
    @NotNull
    @Min(value = 0, message = "Unit must be greather than 0")
    private String unit;
    
    private String image;
}
