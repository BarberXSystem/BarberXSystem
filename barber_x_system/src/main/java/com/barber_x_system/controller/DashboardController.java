package com.barber_x_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	
	@GetMapping("/")
	private String dashboardIndex() {
		return "/Views/SI/Dashboard/dashboardIndex";
	}

}
