package com.fivecafe.body.outbound;

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
public class OutboundDetailResponse {
    //Employee
    private int employeeID;
    private String name;
    private String phone;
    private String image;
    private String username;
    private String password;
    
    //Material
    private int materialID;
    private String materialName;
    private String unit;
    private String materialImage;
    private int quantityInStock;
    
    //Outbound details
    private int quantity;
}