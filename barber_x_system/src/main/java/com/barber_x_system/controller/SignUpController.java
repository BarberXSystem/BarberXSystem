package com.barber_x_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.barber_x_system.entity.Usuario;
import com.barber_x_system.service.IUsuarioServ;

@Controller
@RequestMapping("/registro")
public class SignUpController {
	
	@Autowired
	private IUsuarioServ usuarioServ;

	@GetMapping("/nuevo")
	public String registroUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "/Views/Registro/registroUsuario";
	}
	
	@PostMapping("/nuevo")
	public String registroUsuario(@ModelAttribute Usuario usuario, Model model, RedirectAttributes attr) {
		if (usuarioServ.existePorNumeroDoc(usuario.getNumeroDoc())) {
			model.addAttribute("error", "El numero de documento ya se encuentra registrado!");
			model.addAttribute("usuario", usuario);
			return "/Views/Registro/registroUsuario";
		} else if (usuarioServ.existePorEmail(usuario.getEmail())) {
			model.addAttribute("error", "El correo electronico ingresado ya se encuentra registrado!");
			model.addAttribute("usuario", usuario);
			return "/Views/Registro/registroUsuario";
		} else if (!usuarioServ.passMatch(usuario)) {
			model.addAttribute("error", "Las contraseñas ingresadas no coinciden!");
			model.addAttribute("usuario", usuario);
			return "/Views/Registro/registroUsuario";
		}
		usuario.setEnabled(true);
		usuarioServ.guardar(usuario);
		attr.addFlashAttribute("success", "Usuario creado correctamente!");
		return "/Views/Registro/registroSuccess";	
	}
	
}
