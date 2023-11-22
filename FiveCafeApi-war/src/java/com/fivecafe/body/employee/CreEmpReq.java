package com.fivecafe.body.employee;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
// Abbreviation for Create Employee Request
public class CreEmpReq {
    @NotEmpty(message = "Role ID is required")
    private String roleID;
    
    @NotEmpty(message = "Name is required")
    private String name;
    
    @NotEmpty(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 numbers")
    private String phone;
    
    @NotEmpty(message = "Username is required")
    @Pattern(regexp = "^[a-z0-9]+$", message = "Username only accept a-z, 0-9")
    private String username;
    
    @NotEmpty(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be greater than 8 characters and less than 100 characters")
    private String password;
    
    // No validation
    private String image;
}
