package com.barber_x_system.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.barber_x_system.service.ICitaServ;

@Controller

@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
	private ICitaServ citaService;
	
	@SuppressWarnings("deprecation")
	@GetMapping("/")
	private String dashboardIndex(Model model) {
		Date fechaActual = new Date();
		
		fechaActual.setHours(0);
		fechaActual.setMinutes(0);
		fechaActual.setSeconds(0);
		
		model.addAttribute("citasDeHoy", citaService.buscarPorFechaAndEstado(fechaActual, "PROGRAMADA"));
		return "/Views/SI/Dashboard/dashboardIndex";
	}

}
