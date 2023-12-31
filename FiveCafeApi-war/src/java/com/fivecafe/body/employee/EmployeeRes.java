package com.fivecafe.body.employee;

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
public class EmployeeRes {
    private int employeeID;
    private String roleID;
    private String roleName;
    private String name;
    private String image;
    private String phone;
    private String username;
}
