/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.models.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
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
public class CreateProductResponse {
    @NotEmpty(message = "Product name cannot be empty")
    private String name;
    
    @NotEmpty(message = "Price cannot be empty")
    @Min(value = 0, message = "Price must be greather than 0")
    private double price;
    
    @NotEmpty(message = "Is selling cannot be empty")
    @Pattern(regexp = "^(true|false)$", message = "The value must be 'true' or 'false'.")
    private boolean isSelling;
    
    @NotEmpty(message = "Image cannot be empty")
    private String image;
    
    @NotEmpty(message = "Product category ID cannot be empty")
    private int productCategoryID;
}
