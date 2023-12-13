/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.body.outbound;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
public class CreateOutboundDetailReq {
    @NotNull(message = "Outbound ID is required")
    private int outboundID;
    
    @NotNull(message = "Material ID is required")
    private int materialID;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Min quantity is 1")
    private int quantity;
}
