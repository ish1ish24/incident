package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageCallingController {
	
	
	@GetMapping("/")
	public String getNavbar() {
        return "index"; // Returns the navbar.html template
    }

	@GetMapping("/register")
	public String getRegisternpage() {
        return "fragment/registration"; // Returns the navbar.html template
    }

	
	@GetMapping("/incident")
	public String getIncodent() {
        return "incident"; // Returns the navbar.html template
    }

	
	@GetMapping("/login")
    public String login() {
        return "fragment/login"; // Return the name of your login HTML file
    }

}
