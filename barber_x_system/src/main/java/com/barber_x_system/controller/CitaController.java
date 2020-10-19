package com.barber_x_system.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.barber_x_system.entity.Cita;
import com.barber_x_system.entity.DetalleCita;
import com.barber_x_system.entity.Estilista;
import com.barber_x_system.entity.Usuario;
import com.barber_x_system.service.ICitaServ;
import com.barber_x_system.service.IEstilistaServ;
import com.barber_x_system.service.IProductoServicioServ;
import com.barber_x_system.service.IUsuarioServ;

@Controller
@RequestMapping("/cita")
public class CitaController {
	
	@Autowired
	private IEstilistaServ estilistaServ;
	
	@Autowired
	private ICitaServ citaService;
	
	@Autowired
	private IUsuarioServ usuarioService;
	
	@Autowired
	private IProductoServicioServ prodServService;
	
	private Cita cita = new Cita();
	
	private List<DetalleCita> serviciosAgregados = new ArrayList<DetalleCita>();

	@GetMapping("/")
	public String citas() {
		return "/Views/SI/Citas/citas";
	}
	
	@GetMapping("/cliente/")
	public String citasCliente(Model model, Principal principal) {
		this.serviciosAgregados = new ArrayList<DetalleCita>();
		Usuario usuario = usuarioService.buscarPorNumeroDoc(principal.getName());
		this.cita = new Cita();
		Cita citaAux = new Cita();
		citaAux.setUsuario(usuario);
		
		model.addAttribute("estilistas", estilistaServ.listar());
		model.addAttribute("cita", citaAux);
		return "/Views/SI/Citas/citasCliente";
	}
	
	@PostMapping("/cliente/disponibilidad")
	public String disponibilidad(@ModelAttribute Cita cita, RedirectAttributes attr, Model model) {
		Estilista estilista = estilistaServ.buscarPorId(cita.getEstilista().getIdEstilista());
		List<String> turnosDisponibles = citaService.turnosDisponibles(cita.getFecha(), estilista);
		cita.setEstilista(estilista);
		this.cita = cita;
		
		if (turnosDisponibles == null) {
			attr.addFlashAttribute("error", "El estilista no tiene disponibilidad para el dia seleccionado!");
			return "redirect:/cita/cliente/";
		}
		
		attr.addFlashAttribute("turnos", turnosDisponibles);
		attr.addFlashAttribute("cita", cita);
		return "redirect:/cita/cliente/hora";
	}
	
	@GetMapping("/cliente/hora")
	public String hora(Model model) {
		if (this.cita.getUsuario() == null) {
			return "redirect:/cita/cliente/";
		}
		model.addAttribute("cita", this.cita);
		return "/Views/SI/Citas/horaCita";
	}
	
	@PostMapping("/cliente/hora")
	public String hora(@ModelAttribute Cita cita) {
		this.cita = cita;
		return "redirect:/cita/cliente/servicios";
	}
	
	@GetMapping("/cliente/servicios")
	public String servicios(Model model) {
		model.addAttribute("cita", this.cita);
		
		if (this.cita.getUsuario() == null) {
			return "redirect:/cita/cliente/";
		}
		
		model.addAttribute("servicios", prodServService.buscarServicios());
		model.addAttribute("agregados", this.serviciosAgregados);
		return "/Views/SI/Citas/serviciosCita";
	}
	
	@PostMapping("/cliente/solicitar")
	public String servicios(RedirectAttributes attr) {
		attr.addFlashAttribute("success", "Cita solicitada correctamente!");
		return "redirect:/cita/cliente/";
	}
	
	@GetMapping("/cliente/cancelar")
	public String cancelar() {
		this.cita = new Cita();
		return "redirect:/cita/cliente/";
	}
	
}
