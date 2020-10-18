package com.barber_x_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.barber_x_system.entity.Cita;

@Controller
@RequestMapping("/cita")
public class CitaController {

	@GetMapping("/")
	public String citas() {
		return "/Views/SI/Citas/citas";
	}
	
	@GetMapping("/cliente/")
	public String citasCliente(Model model) {
		model.addAttribute("cita", new Cita());
		return "/Views/SI/Citas/citasCliente";
	}
	
}
