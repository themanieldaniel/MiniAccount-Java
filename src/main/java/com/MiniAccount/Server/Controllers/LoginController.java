package com.MiniAccount.Server.Controllers;

import com.MiniAccount.Server.Services.ChoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private ChoreService choreService;

    @GetMapping("/login")
    public String login() {
        // Mark incomplete chores
        choreService.markIncompleteChores();
        return "login"; // Thymeleaf will look for login.html in the templates folder
    }

}