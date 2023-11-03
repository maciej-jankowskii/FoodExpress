package com.foodapp.controller;

import com.foodapp.model.user.UserRegistrationDTO;
import com.foodapp.model.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "home/index";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        UserRegistrationDTO user = new UserRegistrationDTO();
        model.addAttribute("user", user);
        return "/home/register-form";
    }

    @PostMapping("/register")
    public String register(UserRegistrationDTO dto) {
        userService.register(dto);
        return "redirect:/confirmation-reg";
    }

    @GetMapping("confirmation-reg")
    public String regConfirmation() {
        return "/home/registration-confirmation";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/home/login-form";
    }

    @GetMapping("/home-page")
    public String homeForm(Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("welcomeMessage", "Witaj, " + username + "!");
        return "home/home-page";
    }
}
