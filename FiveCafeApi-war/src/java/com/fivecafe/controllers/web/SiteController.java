package com.fivecafe.controllers.web;

import com.fivecafe.providers.WebUrlProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SiteController {
    @GetMapping({""+WebUrlProvider.Site.GREET1, ""+WebUrlProvider.Site.GREET2, ""+WebUrlProvider.Site.GREET3})
    public String greet() {
        return "site/greet";
    }
    
    @GetMapping(""+WebUrlProvider.Site.LOGIN)
    public String loginPage() {
        return "site/login";
    }
    
    @GetMapping(""+WebUrlProvider.Site.ROLE)
    public String roleManagementPage() {
        return "site/role";
    }
    
    @GetMapping(""+WebUrlProvider.Site.EMPLOYEE)
    public String employeeManagementPage() {
        return "site/employee";
    }
}
