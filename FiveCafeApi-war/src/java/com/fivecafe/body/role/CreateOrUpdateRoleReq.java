package com.fivecafe.body.role;

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
public class CreateOrUpdateRoleReq {
    @NotEmpty(message = "Role ID is required")
    private String roleID;
    
    @NotEmpty(message = "Role name is required")
    private String roleName;
}
