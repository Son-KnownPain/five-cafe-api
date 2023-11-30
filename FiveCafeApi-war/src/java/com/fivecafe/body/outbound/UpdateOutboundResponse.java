/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.body.outbound;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
public class UpdateOutboundResponse {
    @NotNull
    private int outboundID;
    
    @NotEmpty(message = "Date is required")
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Invalid date format (dd/MM/yyyy)")
    private String date;
    
    @NotNull
    private int employeeID;
    
    @NotNull
    private int materialID;
    
    @NotNull
    @Min(value = 0, message = "Price must be greather than 0")
    private int quantity;
}
