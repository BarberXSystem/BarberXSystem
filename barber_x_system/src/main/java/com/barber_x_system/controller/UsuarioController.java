package com.barber_x_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.barber_x_system.entity.Usuario;
import com.barber_x_system.service.IUsuarioServ;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private IUsuarioServ usuarioServ;
	
	@GetMapping("/")
	public String listar(Model model) {
		model.addAttribute("usuarios", usuarioServ.listar());
		model.addAttribute("usuario", new Usuario());
		return "/Views/SI/Usuario/usuarios";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") Long idUsuario, RedirectAttributes attr, Model model) {
		Usuario usuario = null;
		
		if (idUsuario > 0) {
			usuario = usuarioServ.buscarPorId(idUsuario);
			
			if (usuario == null) {
				attr.addFlashAttribute("error", "El ID del usuario que intenta editar no existe!");
				return "redirect:/usuario/";
			}
			
		} else {
			attr.addFlashAttribute("error", "El ID del usuario que intenta editar no existe!");
			return "redirect:/usuario/";
		}
		
		model.addAttribute("usuario", usuario);
		return "/Views/SI/Usuario/editarUsuario";
	}
	
	@PostMapping("/editar")
	public String editar(@ModelAttribute Usuario usuario, RedirectAttributes attr) {
		usuarioServ.guardar(usuario);
		attr.addFlashAttribute("success", "Usuario actualizado correctamente!");
		return "redirect:/usuario/";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") Long idUsuario, RedirectAttributes attr) {
		Usuario usuario = null;
		
		if (idUsuario > 0) {
			usuario = usuarioServ.buscarPorId(idUsuario);
			
			if (usuario == null) {
				attr.addFlashAttribute("error", "El ID del usuario que intenta eliminar no existe!");
				return "redirect:/usuario/";
			}
			
		} else {
			attr.addFlashAttribute("error", "El ID del usuario que intenta eliminar no existe!");
			return "redirect:/usuario/";
		}
		
		usuarioServ.eliminar(idUsuario);
		attr.addFlashAttribute("warning", "Usuario eliminado correctamente!");
		return "redirect:/usuario/";
	}

}
