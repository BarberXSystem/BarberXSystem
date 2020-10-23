package com.barber_x_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.barber_x_system.service.IProductoServicioServ;

@Controller
@RequestMapping("/ingreso")
public class IngresoController {
	
	@Autowired
	private IProductoServicioServ servProdServ;
	
	@GetMapping("/")
	public String listar() {
		return "/Views/SI/Ingresos/ingresos";
	}
	
	@GetMapping("/index")
	public String ingresoIndex(Model model) {
		model.addAttribute("prodsServs", servProdServ.listar());
		return "/Views/SI/Ingresos/ingresoIndex";
	}
	
	

}
