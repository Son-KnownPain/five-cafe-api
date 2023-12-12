package com.fivecafe.models.product;

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
public class ProductResponse {
    private int productID;
    private int productCategoryID;
    private String productCategoryName;
    private String name;
    private double price;
    private boolean isSelling;
    private String image;
}
