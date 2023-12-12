package com.fivecafe.body.material;

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
public class MaterialResponse {
    private int materialID;
    private int materialCategoryID;
    private String materialCategoryName;
    private String name;
    private String unit;
    private int quantityInStock;
    private String image;
    
    
}
