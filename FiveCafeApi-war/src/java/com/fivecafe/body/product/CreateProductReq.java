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
public class CreateProductReq {

    @NotNull(message = "Product category ID is required")
    private int productCategoryID;

    @NotEmpty(message = "Product name cannot be empty")
    private String name;

    @NotNull(message = "Price is required")
    private double price;

    @NotNull(message = "Is Selling is required")
    private boolean selling;

    private String image;

}
