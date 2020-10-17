package com.barber_x_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping()
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/prueba")
	public String prueba() {
		return "/Views/Registro/registroUsuario";
	}

}
