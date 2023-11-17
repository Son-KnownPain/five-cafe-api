package com.fivecafe.body.supplier;

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
public class UpdateAndDeleteSuplier {
    private int supplierID;
    @NotEmpty(message = "Contact name cannot be empty")
    private String contactName;
    @NotEmpty(message = "Contact number cannot be empty")
    private String contactNumber;
    @NotEmpty(message = "Address cannot be empty")   
    private String address;
}
