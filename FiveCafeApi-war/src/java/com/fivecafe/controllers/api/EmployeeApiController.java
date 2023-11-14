package com.fivecafe.controllers.api;

import com.fivecafe.providers.UrlProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Employee.PREFIX)
public class EmployeeApiController {
    @GetMapping("" + UrlProvider.Employee.TEST)
    public String test() {
        return "test";
    }
}
