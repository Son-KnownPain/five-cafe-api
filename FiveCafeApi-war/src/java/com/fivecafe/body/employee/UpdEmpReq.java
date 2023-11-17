package com.fivecafe.body.employee;

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
public class UpdEmpReq {
    @NotNull(message = "Employee ID is required")
    private int employeeID;
    
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
    
    private String password;
}
