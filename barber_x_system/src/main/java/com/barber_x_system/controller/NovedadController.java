package com.barber_x_system.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.barber_x_system.entity.Estilista;
import com.barber_x_system.service.IEstilistaServ;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/novedad")
public class NovedadController {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private IEstilistaServ estilistaService;
	
	@GetMapping("/")
	public String compartirNovedad() {
		return "/Views/SI/Novedad/compartirNovedad";
	}
	
	@PostMapping("/")
	public String compartir(@RequestParam("asunto") String asunto, @RequestParam("mensaje") String mensaje,
			RedirectAttributes attr, Model model) 
			throws MessagingException, UnsupportedEncodingException{
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		String mailContent = "<p><b>Asunto:</b> " + asunto + "</p>";
		mailContent += "<p><b>Mensaje:</b> " + mensaje + "</p>";
		
		try {
			helper.setTo(correosEstilistas());
			helper.setSubject(asunto);
			helper.setText(mailContent, true);
			
			mailSender.send(message);
		} catch (Exception e) {
			attr.addFlashAttribute("error", e.getMessage());
			return "redirect:/novedad/";
		}
		
		attr.addFlashAttribute("success", "Novedad compartida correctamente!");
		return "redirect:/novedad/";
	}
	
	public String[] correosEstilistas() {
		List<Estilista> estilistas = estilistaService.listar();
		String correos[] = new String[estilistas.size()];
		
		for (int i = 0; i < estilistas.size(); i++) {
			correos[i] = estilistas.get(i).getUsuario().getEmail().toLowerCase();
		}
		
		return correos;
	}

}
