
package com.fivecafe.body.supplier;

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
public class CreateSupplier {
    @NotEmpty(message = "Contact name cannot be empty")
    private String contactName;
    
    @NotEmpty(message = "Phone is required")
    @Pattern(regexp = "0[0-9]{9}", message = "The phone is invalid, only receives 10 numbers and starts with 0")
    private String contactNumber;
    
    @NotEmpty(message = "Address cannot be empty")        
    private String address;
}
