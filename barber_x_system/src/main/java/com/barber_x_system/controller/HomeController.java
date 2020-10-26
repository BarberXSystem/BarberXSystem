package com.barber_x_system.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.barber_x_system.service.IProductoServicioServ;

@Controller
public class HomeController {
	
	@Autowired
	private IProductoServicioServ servProdService;
	
	@GetMapping({"/", "/home", "/index"})
	public String index(Principal principal, Model model) {
//		if (principal != null) {
//			return "redirect:/dashboard/";
//		}
		model.addAttribute("productos", servProdService.buscarProductos());
		model.addAttribute("servicios", servProdService.buscarServicios());
		return "index";
	}
	
	@GetMapping("/servicios")
	public String servicios() {
		return "servicios";
	}
	
	@GetMapping("/about-us")
	public String acercaDe() {
		return "about";
	}

}
