package com.fivecafe.controllers.web;

import com.fivecafe.providers.WebUrlProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MenuController {
    @GetMapping(""+WebUrlProvider.Menu.MENU)
    public String menu() {
        return "menu/menu";
    }
}
