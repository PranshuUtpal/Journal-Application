package net.pranshhvoyages.journalApp.controller;

import net.pranshhvoyages.journalApp.entity.User;
import net.pranshhvoyages.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartOfApplication {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String showLoginAndSignupPage(){
        System.out.println("Hello method call");
        return "mainpage";
    }
    @GetMapping("/signin")
    public String indexpage(Model model) {
        model.addAttribute("user", new User());
        return "index";  // This returns the Thymeleaf template located in src/main/resources/templates/index.html
    }
    @GetMapping("/homepage")
    public String showHomePage(){
        System.out.println("Hello method call");
        return "homepage";
    }

    @GetMapping("/greeting")
    public String greeting(Model model) {
        model.addAttribute("message", "Welcome to our website!");
        return "healthcheck"; // Thymeleaf template name (greeting.html)
    }
}
