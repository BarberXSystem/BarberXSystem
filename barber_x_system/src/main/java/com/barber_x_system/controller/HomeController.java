package com.barber_x_system.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping({"/", "/home", "/index"})
	public String index(Principal principal) {
//		if (principal != null) {
//			return "redirect:/dashboard/";
//		}
		return "index";
	}

}
