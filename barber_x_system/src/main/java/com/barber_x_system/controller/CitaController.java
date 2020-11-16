package com.barber_x_system.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

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
@Component("/Views/SI/Citas/citas.xlsx")
public class CitaController extends AbstractXlsxView{
	
	@Autowired
	private IEstilistaServ estilistaServ;
	
	@Autowired
	private ICitaServ citaService;
	
	@Autowired
	private IUsuarioServ usuarioService;
	
	@Autowired
	private IProductoServicioServ prodServService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	private Cita cita = new Cita();
	
	private List<Cita> citas = new ArrayList<>();
	
	private List<Cita> citasX;

	@Secured("ROLE_ADMIN")
	@GetMapping("/")
	public String citas(Model model) {
		this.citas = citaService.listar();
		List<Cita> programadas = citaService.buscarPorEstado("PROGRAMADA");
		List<Cita> finalizadas = citaService.buscarPorEstado("FINALIZADA");
		List<Cita> canceladas = citaService.buscarPorEstado("CANCELADA");
		
		Map<String, Integer> citasMap = new LinkedHashMap<>();
		
		citasMap.put("PROGRAMADAS", programadas.size());
		citasMap.put("FINALIZADAS", finalizadas.size());
		citasMap.put("CANCELADAS", canceladas.size());
		
		model.addAttribute("citasMap", citasMap);
		model.addAttribute("totalCitas", this.citas.size());
		model.addAttribute("todas", this.citas);
		
		return "/Views/SI/Citas/citas";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/")
	public String buscar(@RequestParam("estado") String estado, Model model, RedirectAttributes attr) {
		
		if (estado.isEmpty()) {
			this.citasX = null;
			attr.addFlashAttribute("warning", "No se encontraron valores en la busqueda!");
			return "redirect:/cita/";
		}
		
		this.citas = citaService.buscarPorEstado(estado);
		this.citasX = this.citas;
		
		if (this.citas.isEmpty()) {
			this.citasX = null;
			attr.addFlashAttribute("warning", "No se encontraron resultados!");
			return "redirect:/cita/";
		}
		
		model.addAttribute("todas", this.citas);
		return "/Views/SI/Citas/citas";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/limpiar")
	public String limpiar() {
		this.citasX = null;
		return "redirect:/cita/";
	}
	
	@Secured("ROLE_ADMIN")
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
	
	@Secured({"ROLE_ADMIN", "ROLE_ESTILISTA"})
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
	
	@Secured("ROLE_USER")
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
	
	@Secured("ROLE_USER")
	@GetMapping("/cliente/")
	public String nuevaCita(Model model, Principal principal) {
		Usuario usuario = usuarioService.buscarPorNumeroDoc(principal.getName());
		this.cita = new Cita();
		this.cita.setUsuario(usuario);
		
		model.addAttribute("estilistas", estilistaServ.listar());
		model.addAttribute("cita", this.cita);
		return "/Views/SI/Citas/solicitarCita";
	}
	
	@Secured("ROLE_USER")
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
	
	@Secured("ROLE_USER")
	@GetMapping("/cliente/hora")
	public String hora(Model model) {
		if (this.cita.getUsuario() == null) {
			return "redirect:/cita/cliente/";
		}
		model.addAttribute("cita", this.cita);
		return "/Views/SI/Citas/horaCita";
	}
	
	@Secured("ROLE_USER")
	@PostMapping("/cliente/hora")
	public String hora(@ModelAttribute Cita cita) {
		this.cita.setHora(cita.getHora());;
		return "redirect:/cita/cliente/servicios";
	}
	
	@Secured("ROLE_USER")
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
	
	@Secured("ROLE_USER")
	@SuppressWarnings("deprecation")
	@PostMapping("/cliente/solicitar")
	public String servicios(@RequestParam("idServicio") Long idServicio, RedirectAttributes attr,
			Model model) throws MessagingException, UnsupportedEncodingException {
		ProductoServicio servicio = prodServService.buscarPorId(idServicio);
		this.cita.setEstado("PROGRAMADA");
		this.cita.setServicio(servicio);
		
		try {
			citaService.guardar(this.cita);
			
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			String mensaje = "<p><b>ESTILISTA: </b>"+this.cita.getEstilista().getUsuario().getNombres()+" "+this.cita.getEstilista().getUsuario().getApellidos()+"</p>";
			mensaje += "<p><b>CITA NUMERO: </b>"+this.cita.getIdCita()+"</p>";
			mensaje += "<p><b>SERVICIO: </b>"+this.cita.getServicio().getNombre()+"</p>";
			mensaje += "<p><b>FECHA: </b>"+this.cita.getFecha().toLocaleString()+"</p>";
			mensaje += "<p><b>HORA: </b>"+this.cita.getHora()+"</p>";
			
			helper.setTo(this.cita.getUsuario().getEmail().toLowerCase());
			helper.setSubject("CITA ASIGNADA EXISTOSAMENTE");
			helper.setText(mensaje, true);
			
			mailSender.send(message);
		} catch (Exception e) {
			model.addAttribute("servicios", prodServService.buscarServicios());
			model.addAttribute("cita", this.cita);
			model.addAttribute("error", e.getMessage());
			return "/Views/SI/Citas/serviciosCita";
		}
		
		
		attr.addFlashAttribute("success", "Cita programada correctamente!");
		return "redirect:/cita/cliente/citas";
	}
	
	@Secured("ROLE_USER")
	@GetMapping("/cliente/cancelar")
	public String clienteCancelar() {
		this.cita = new Cita();
		return "redirect:/cita/cliente/";
	}
	
	@Secured("ROLE_USER")
	@GetMapping("/cancelar")
	public String cancelarCita() {
		return "/Views/SI/Citas/cancelarCita";
	}
	
	@Secured("ROLE_USER")
	@PostMapping("/cancelar")
	public String cancelar(@RequestParam("idCita") Long idCita, Model model, Principal principal) {
		Cita cita = citaService.buscarPorId(idCita);
		Usuario usuario = usuarioService.buscarPorNumeroDoc(principal.getName());
		
		if (cita == null) {
			model.addAttribute("error", "No se ha encontrado ninguna cita con el numero ingresado!");
			return "/Views/SI/Citas/cancelarCita";
		} else if (cita.getEstado().equals("FINALIZADA")) {
			model.addAttribute("error", "La cita que desea cancelar ya se encuentra finalizada!");
			return "/Views/SI/Citas/cancelarCita";
		} else if (cita.getEstado().equals("CANCELADA")) {
			model.addAttribute("error", "La cita que desea cancelar ya se encuentra cancelada!");
			return "/Views/SI/Citas/cancelarCita";
		} else if (!citaService.validCancelCita(cita)){
			model.addAttribute("error", "La cita que desea cancelar ya no se puede cancelar por tiempo!");
			return "/Views/SI/Citas/cancelarCita";
		} else if (!citaService.validOwnCita(usuario, cita)) {
			model.addAttribute("error", "La cita que desea cancelar no puede ser cancelada por usted!");
			return "/Views/SI/Citas/cancelarCita";
		}
		
		
		cita.setEstado("CANCELADA");
		citaService.guardar(cita);
		model.addAttribute("success", "Cita cancelada correctamente!");
		return "/Views/SI/Citas/cancelarCita";
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/estilista")
	public String citasEstilista(Model model, Principal principal) {
		Usuario usuario = usuarioService.buscarPorNumeroDoc(principal.getName());
		Estilista estilista = estilistaServ.buscarPorUsuario(usuario);
		
		Date fechaActual = new Date();
		
		fechaActual.setHours(0);
		fechaActual.setMinutes(0);
		fechaActual.setSeconds(0);
		
		List<Cita> citasEstilista = citaService.buscarPorFechaAndEstilista(fechaActual, estilista);
		model.addAttribute("citas", citasEstilista);
		return "/Views/SI/Citas/citasEstilista";
	}

	@SuppressWarnings("deprecation")
	@Secured("ROLE_ADMIN")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			response.setHeader("Content-Disposition", "attachment; filename=\"citas.xlsx\"");
			Sheet hoja = workbook.createSheet("Citas");
			
			CellStyle style = workbook.createCellStyle();
			XSSFFont font = (XSSFFont) workbook.createFont();
			font.setBold(true);
			font.setFontHeight(16);
			style.setFont(font);

			Row filaTitulo = hoja.createRow(0);
			Cell celda = filaTitulo.createCell(0);
			celda.setCellValue("");
			celda.setCellStyle(style);

			Row filaData = hoja.createRow(0);
			String[] columnas = { "ID", "Cedula cliente", "Cliente", "Estilista", "Servicio", 
					"Fecha", "Hora", "Estado"};

			for (int i = 0; i < columnas.length; i++) {
				celda = filaData.createCell(i);
				celda.setCellValue(columnas[i]);
				celda.setCellStyle(style);
			}

			int numFila = 1;
			
			if (this.citasX == null) {
				this.citasX = citaService.listar();
			}

			for (Cita cita : this.citasX) {
				filaData = hoja.createRow(numFila);

				filaData.createCell(0).setCellValue(cita.getIdCita());
				hoja.autoSizeColumn(0);
				filaData.createCell(1).setCellValue(cita.getUsuario().getNumeroDoc());
				hoja.autoSizeColumn(1);
				filaData.createCell(2).setCellValue(cita.getUsuario().getNombres()+" "+cita.getUsuario().getApellidos());
				hoja.autoSizeColumn(2);
				filaData.createCell(3).setCellValue(cita.getEstilista().getUsuario().getNombres()+" "+cita.getEstilista().getUsuario().getApellidos());
				hoja.autoSizeColumn(3);
				filaData.createCell(4).setCellValue(cita.getServicio().getNombre());
				hoja.autoSizeColumn(4);
				String fecha = cita.getFecha().getDate()+"-"+(cita.getFecha().getMonth()+1)+"-"+(cita.getFecha().getYear()+1900);
				filaData.createCell(5).setCellValue(fecha);
				hoja.autoSizeColumn(5);
				filaData.createCell(6).setCellValue(cita.getHora());
				hoja.autoSizeColumn(6);
				filaData.createCell(7).setCellValue(cita.getEstado());
				hoja.autoSizeColumn(7);
				numFila++;
			}
	}
	
}
