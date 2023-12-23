package com.fivecafe.body.product;

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
public class CreateProductResponse {

    @NotNull
    private int productCategoryID;

    @NotEmpty(message = "Product name cannot be empty")
    private String name;

    @NotNull
    private double price;

    @NotNull
    private boolean isSelling;

    private String image;

}
