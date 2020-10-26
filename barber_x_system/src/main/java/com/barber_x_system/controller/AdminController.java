package com.barber_x_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.barber_x_system.entity.Administrador;
import com.barber_x_system.entity.Rol;
import com.barber_x_system.service.IAdminServ;
import com.barber_x_system.service.IRolServ;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private IAdminServ adminService;
	
	@Autowired
	private IRolServ rolService;
	
	@GetMapping("/")
	public String listar(Model model) {
		model.addAttribute("admins", adminService.listar());
		return "/Views/SI/Admin/administradores";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") Long idAdmin, RedirectAttributes attr) {
		Administrador admin = null;
		
		if (idAdmin > 0) {
			admin = adminService.buscarPorId(idAdmin);
			
			if (admin == null) {
				attr.addFlashAttribute("error", "El ID del administrador que intenta eliminar no existe!");
				return "redirect:/admin/";
			}
			
		} else {
			attr.addFlashAttribute("error", "El ID del administrador que intenta eliminar no existe!");
			return "redirect:/admin/";
		}
		
		adminService.eliminar(idAdmin);
		Rol rol = rolService.buscarPorUsuarioAndRol(admin.getUsuario(), "ROLE_ADMIN");
		rolService.eliminar(rol.getIdRol());
		attr.addFlashAttribute("warning", "Administrador eliminado correctamente!");
		return "redirect:/admin/";
	}

}
