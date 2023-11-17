package com.fivecafe.body.materialcategory;

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
public class MaterialCategoryResponse {
    private int materialCategoryID;
    private String name;
    private String description;
}
