package com.barber_x_system.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String home(@RequestParam(value = "error", required = false) String error, Model model,
			Principal principal, RedirectAttributes attr, @RequestParam(value = "logout", required = false) String logout) {
		
		if (error != null) {
			model.addAttribute("error", "ERROR DE ACCESO: Usurio y/o contraseña son incorrectos!");
		}
		
		if (logout != null) {
			model.addAttribute("success", "Sesion finalizada correctamente!");
		}
		
		if (principal != null) {
			attr.addFlashAttribute("warning", "Usted ya ha iniciado sesion previamente!");
			return "redirect:/dashboard/";
		}
		
		return "login";
	}

}
