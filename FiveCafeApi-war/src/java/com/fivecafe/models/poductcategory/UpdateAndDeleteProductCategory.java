/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.models.poductcategory;

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
public class UpdateAndDeleteProductCategory {
    @NotNull(message = "Product category ID cannot be empty")
    private int productCategoryID;
    @NotEmpty(message = "Product category name cannot be empty")
    private String name;
    @NotEmpty(message = "Product category description cannot be empty")
    private String description;
}
