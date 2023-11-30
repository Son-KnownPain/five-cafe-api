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
public class EmpLoginCredentials {
    @NotEmpty(message = "Username is required")
    @Pattern(regexp = "^[a-z0-9]+$", message = "Username only accept a-z, 0-9")
    private String username;
    
    @NotEmpty(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be greater than 8 characters and less than 100 characters")
    private String password;
}
