package com.barber_x_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.barber_x_system.entity.Administrador;
import com.barber_x_system.entity.Estilista;
import com.barber_x_system.entity.Rol;
import com.barber_x_system.entity.Usuario;
import com.barber_x_system.service.IAdminServ;
import com.barber_x_system.service.IEstilistaServ;
import com.barber_x_system.service.IRolServ;
import com.barber_x_system.service.IUsuarioServ;

@Controller
@RequestMapping("/rol")
public class RolController {
	
	@Autowired
	private IRolServ rolService;
	
	@Autowired
	private IUsuarioServ usuarioService;
	
	@Autowired
	private IAdminServ adminService;
	
	@Autowired
	private IEstilistaServ estilistaService;
	
	@GetMapping("/")
	public String listar(Model model) {
		model.addAttribute("roles", rolService.listar());
		return "/Views/SI/Permisos/permisos";
	}
	
	@PostMapping("/nuevo")
	public String nuevoRol(@RequestParam("numeroDocMA") String numeroDocMA, @RequestParam("rolMA") String rolMA,
			RedirectAttributes attr) {
		Usuario usuario = usuarioService.buscarPorNumeroDoc(numeroDocMA);
		Rol rol = new Rol();
		
		if (usuario == null) {
			attr.addFlashAttribute("error", "Usuario no encontrado!");
			return "redirect:/rol/";
		} else if (rolService.existePorUsuarioAndRol(usuario, rolMA)) {
			attr.addFlashAttribute("error", "El usuario ya cuenta con ese permiso!");
			return "redirect:/rol/";
		}
		
		rol.setUsuario(usuario);
		rol.setRol(rolMA);
		
		rolService.guardar(rol);
		attr.addFlashAttribute("success", "Permiso agregado correctamente!");
		return "redirect:/rol/";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") Long idRol, RedirectAttributes attr) {
		Rol rol = null;
		
		if (idRol > 0) {
			rol = rolService.buscarPorId(idRol);
			
			if (rol == null) {
				attr.addFlashAttribute("error", "El ID del administrador que intenta eliminar no existe!");
				return "redirect:/rol/";
			}
			
		} else {
			attr.addFlashAttribute("error", "El ID del administrador que intenta eliminar no existe!");
			return "redirect:/rol/";
		}
		
		switch (rol.getRol()) {
		case "ROLE_USER":
			Usuario usuario = rol.getUsuario();
			rolService.eliminar(idRol);
			usuarioService.eliminar(usuario.getIdUsuario());
			break;
		case "ROLE_ADMIN":
			Administrador admin = adminService.buscarPorUsuario(rol.getUsuario());
			rolService.eliminar(idRol);
			adminService.eliminar(admin.getIdAdmin());
			break;
		case "ROLE_ESTILISTA":
			Estilista estilista = estilistaService.buscarPorUsuario(rol.getUsuario());
			rolService.eliminar(idRol);
			estilistaService.eliminar(estilista.getIdEstilista());
			break;

		default:
			System.out.println("NO SE ESCOJIO NINGUNA OPCION");;
			attr.addFlashAttribute("warning", "Administrador eliminado correctamente!");
			return "redirect:/rol/";
		}
		attr.addFlashAttribute("warning", "Administrador eliminado correctamente!");
		return "redirect:/rol/";
	}
	
}
