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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.barber_x_system.entity.Cita;
import com.barber_x_system.entity.Estilista;
import com.barber_x_system.entity.ProductoServicio;
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

	@GetMapping("/")
	public String citas(Model model) {
		model.addAttribute("todas", citaService.listar());
		return "/Views/SI/Citas/citas";
	}
	
	@GetMapping("/canceladas")
	public String citasCanceladas(Model model) {
		List<Cita> citas = citaService.listar();
		List<Cita> canceladas = new ArrayList<>();
		
		for (Cita cita : citas) {
			if (cita.getEstado().equals("CANCELADA")) {
				canceladas.add(cita);
			}
		}
		
		model.addAttribute("canceladas", canceladas);
		return "/Views/SI/Citas/citasCanceladas";
	}
	
	@GetMapping("/programadas")
	public String citasProgramadas(Model model) {
		List<Cita> citas = citaService.listar();
		List<Cita> programadas = new ArrayList<>();
		
		for (Cita cita : citas) {
			if (cita.getEstado().equals("PROGRAMADA")) {
				programadas.add(cita);
			}
		}
		model.addAttribute("programadas", programadas);
		return "/Views/SI/Citas/citasProgramadas";
	}
	
	@GetMapping("/cliente/citas")
	public String citasCliente(Model model, Principal principal) {
		List<Cita> citas = citaService.buscarPorUsuario(usuarioService.buscarPorNumeroDoc(principal.getName()));
		List<Cita> misCitas = new ArrayList<>();
		
		for (Cita cita : citas) {
			if (cita.getEstado().equals("PROGRAMADA")) {
				misCitas.add(cita);
			}
		}
		
		model.addAttribute("citas", misCitas);
		return "/Views/SI/Citas/citasCliente";
	}
	
	@GetMapping("/cliente/")
	public String nuevaCita(Model model, Principal principal) {
		Usuario usuario = usuarioService.buscarPorNumeroDoc(principal.getName());
		this.cita = new Cita();
		this.cita.setUsuario(usuario);
		
		model.addAttribute("estilistas", estilistaServ.listar());
		model.addAttribute("cita", this.cita);
		return "/Views/SI/Citas/solicitarCita";
	}
	
	@PostMapping("/cliente/disponibilidad")
	public String disponibilidad(@ModelAttribute Cita cita, RedirectAttributes attr, Model model) {
		Estilista estilista = estilistaServ.buscarPorId(cita.getEstilista().getIdEstilista());
		List<String> turnosDisponibles = citaService.turnosDisponibles(cita.getFecha(), estilista);
		this.cita.setEstilista(estilista);
		this.cita.setFecha(cita.getFecha());
		
		if (turnosDisponibles == null) {
			attr.addFlashAttribute("error", "El estilista no tiene disponibilidad para el dia seleccionado!");
			return "redirect:/cita/cliente/";
		}
		
		attr.addFlashAttribute("turnos", turnosDisponibles);
		attr.addFlashAttribute("cita", this.cita);
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
		this.cita.setHora(cita.getHora());;
		return "redirect:/cita/cliente/servicios";
	}
	
	@GetMapping("/cliente/servicios")
	public String servicios(Model model) {
		model.addAttribute("cita", this.cita);
		
		if (this.cita.getUsuario() == null) {
			return "redirect:/cita/cliente/";
		}
		
		model.addAttribute("servicios", prodServService.buscarServicios());
		model.addAttribute("cita", this.cita);
		return "/Views/SI/Citas/serviciosCita";
	}
	
	@PostMapping("/cliente/solicitar")
	public String servicios(@RequestParam("idServicio") Long idServicio, RedirectAttributes attr) {
		ProductoServicio servicio = prodServService.buscarPorId(idServicio);
		this.cita.setEstado("PROGRAMADA");
		this.cita.setServicio(servicio);
		
		citaService.guardar(this.cita);
		attr.addFlashAttribute("success", "Cita programada correctamente!");
		return "redirect:/cita/cliente/citas";
	}
	
	@GetMapping("/cliente/cancelar")
	public String clienteCancelar() {
		this.cita = new Cita();
		return "redirect:/cita/cliente/";
	}
	
	@GetMapping("/cancelar")
	public String cancelarCita() {
		return "/Views/SI/Citas/cancelarCita";
	}
	
	@PostMapping("/cancelar")
	public String cancelar(@RequestParam("idCita") Long idCita, Model model) {
		Cita cita = citaService.buscarPorId(idCita);
		if (cita == null) {
			model.addAttribute("error", "No se ha encontrado ninguna cita con el numero ingresado!");
			return "/Views/SI/Citas/cancelarCita";
		} else if (cita.getEstado().equals("FINALIZADA")) {
			model.addAttribute("error", "La cita que desea cancelar ya se encuentra finalizada!");
			return "/Views/SI/Citas/cancelarCita";
		} else if (cita.getEstado().equals("CANCELADA")) {
			model.addAttribute("error", "La cita que desea cancelar ya se encuentra cancelada!");
			return "/Views/SI/Citas/cancelarCita";
		}
		
		cita.setEstado("CANCELADA");
		citaService.guardar(cita);
		model.addAttribute("success", "Cita cancelada correctamente!");
		return "/Views/SI/Citas/cancelarCita";
	}
	
}
