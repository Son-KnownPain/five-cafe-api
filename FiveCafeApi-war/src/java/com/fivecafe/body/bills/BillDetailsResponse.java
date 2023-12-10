package com.fivecafe.body.bills;

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
public class BillDetailsResponse {
//    Bill Details:
    private double unitPrice;
    private int quantity;
    
//    Product
    private int productID;
    private String name;
    private String image;
}
