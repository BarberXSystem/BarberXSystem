package com.barber_x_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.barber_x_system.entity.Estilista;
import com.barber_x_system.entity.Rol;
import com.barber_x_system.service.IEstilistaServ;
import com.barber_x_system.service.IRolServ;

@Controller
@RequestMapping("/estilista")
public class EstilistaController {
	
	@Autowired
	private IEstilistaServ estilistaService;
	
	@Autowired
	private IRolServ rolService;
	
	@GetMapping("/")
	public String listar(Model model) {
		model.addAttribute("estilistas", estilistaService.listar());
		return "/Views/SI/Estilista/estilistas";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") Long idEstilista, RedirectAttributes attr) {
		Estilista estilista = null;
		
		if (idEstilista > 0) {
			estilista = estilistaService.buscarPorId(idEstilista);
			
			if (estilista == null) {
				attr.addFlashAttribute("error", "El ID del estilista que intenta eliminar no existe!");
				return "redirect:/estilista/";
			}
			
		} else {
			attr.addFlashAttribute("error", "El ID del estilista que intenta eliminar no existe!");
			return "redirect:/estilista/";
		}
		
		estilistaService.eliminar(idEstilista);
		Rol rol = rolService.buscarPorUsuarioAndRol(estilista.getUsuario(), "ROLE_ESTILISTA");
		rolService.eliminar(rol.getIdRol());
		attr.addFlashAttribute("warning", "Estilista eliminado correctamente!");
		return "redirect:/estilista/";
	}

}
