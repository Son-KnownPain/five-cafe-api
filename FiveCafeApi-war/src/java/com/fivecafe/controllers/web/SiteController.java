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
    
    @GetMapping(""+WebUrlProvider.Site.BILL_STATUS)
    public String billStatusManagementPage() {
        return "site/bill-status";
    }
    
    @GetMapping(""+WebUrlProvider.Site.PRODUCT_CATEGORIES)
    public String productCategoriesManagementPage() {
        return "site/product-categories";
    }
    
    @GetMapping(""+WebUrlProvider.Site.MATERIAL_CATEGORIES)
    public String materialCategoriesManagementPage() {
        return "site/material-categories";
    }
    
    @GetMapping(""+WebUrlProvider.Site.SHIFTS)
    public String shiftManagementPage() {
        return "site/shift";
    }
    
    @GetMapping(""+WebUrlProvider.Site.SUPPLIER)
    public String supplierManagementPage() {
        return "site/supplier";
    @GetMapping(""+WebUrlProvider.Site.EMPLOYEE)
    public String employeeManagementPage() {
        return "site/employee";
    }
    
    @GetMapping(""+WebUrlProvider.Site.MATERIAL)
    public String materialManagementPage() {
        return "site/material";
    }
    
    @GetMapping(""+WebUrlProvider.Site.PRODUCT)
    public String productManagementPage() {
        return "site/product";
    }
}
