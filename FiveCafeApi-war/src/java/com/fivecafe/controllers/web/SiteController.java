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
     }   
    
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
    
    @GetMapping(""+WebUrlProvider.Site.IMPORT)
    public String importManagementPage() {
        return "site/import";
    }
    
    @GetMapping(""+WebUrlProvider.Site.EMP_SALARY)
    public String empSalaryManagementPage() {
        return "site/emp-salary";
    }
    
    @GetMapping(""+WebUrlProvider.Site.OUTBOUND)
    public String outboundManagementPage() {
        return "site/outbound";
    }

    @GetMapping(""+WebUrlProvider.Site.BILL)
    public String billManagementPage() {
        return "site/bill";
    }

    @GetMapping(""+WebUrlProvider.Site.ORDERING)
    public String orderingPage() {
        return "site/ordering";
    }

    @GetMapping(""+WebUrlProvider.Site.CREATE_OUTBOUND)
    public String createOutboundPage() {
        return "site/create-outbound";
    }

    @GetMapping(""+WebUrlProvider.Site.MY_BILLS)
    public String myBillsPage() {
        return "site/my-bills";
    }

    @GetMapping(""+WebUrlProvider.Site.MY_TIMEKEEPINGS)
    public String myTimekeepingsPage() {
        return "site/my-timekeepings";
    }

    @GetMapping(""+WebUrlProvider.Site.MY_SALARIES)
    public String mySalariesPage() {
        return "site/my-salaries";
    }

    @GetMapping(""+WebUrlProvider.Site.TIMEKEEPING)
    public String timekeepingPage() {
        return "site/etk";
    }

    @GetMapping(""+WebUrlProvider.Site.MY_OUTBOUNDS)
    public String myOutboundsPage() {
        return "site/my-outbounds";
    }

    @GetMapping(""+WebUrlProvider.Site.COST_STATISTIC)
    public String costStatisticPage() {
        return "statistic/all";
    }
}
