package com.barber_x_system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import com.barber_x_system.entity.Cita;
import com.barber_x_system.entity.DetallePago;
import com.barber_x_system.entity.Estilista;
import com.barber_x_system.entity.ProductoServicio;
import com.barber_x_system.entity.ReciboPago;
import com.barber_x_system.entity.Usuario;
import com.barber_x_system.service.ICitaServ;
import com.barber_x_system.service.IDetallePagoServ;
import com.barber_x_system.service.IEstilistaServ;
import com.barber_x_system.service.IProductoServicioServ;
import com.barber_x_system.service.IReciboPagoServ;
import com.barber_x_system.service.IUsuarioServ;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/ingreso")
@Component("/Views/SI/Ingresos/ingresos.xlsx")
public class IngresoController extends AbstractXlsxView{
	
	@Autowired
	private IProductoServicioServ servProdServ;
	
	@Autowired
	private IReciboPagoServ reciboService;
	
	@Autowired
	private IUsuarioServ usuarioService;
	
	@Autowired
	private ICitaServ citaService;
	
	@Autowired
	private IDetallePagoServ detallePagoService;
	
	@Autowired
	private IEstilistaServ estilistaService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	private ReciboPago recibo = new ReciboPago();
	
	private List<DetallePago> detallesAgregados = new ArrayList<>();
	
	private List<ProductoServicio> productosServicios = new ArrayList<>();
	
	private List<Estilista> estilistas = new ArrayList<>();
	
	private List<ReciboPago> ingresos = new ArrayList<>();
	
	private List<ReciboPago> ingresosX;
	
	private boolean mode = false;
	
	@GetMapping("/")
	public String listar(Model model) {
		this.ingresos = reciboService.listar();
		model.addAttribute("ingresos", this.ingresos);
		model.addAttribute("ingreso", new ReciboPago());
		model.addAttribute("estilistas", estilistaService.listar());
		return "/Views/SI/Ingresos/ingresos";
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping("/")
	public String buscar(@ModelAttribute ReciboPago ingreso, Model model, RedirectAttributes attr) {
		Estilista estilista = null;		
		
		if (ingreso.getEstilista().getIdEstilista() != null) {
			estilista = estilistaService.buscarPorId(ingreso.getEstilista().getIdEstilista());
		}
		
		if (ingreso.getFecha() != null) {
			ingreso.getFecha().setHours(0);
			ingreso.getFecha().setMinutes(0);
			ingreso.getFecha().setSeconds(0);
		}
		
		if (ingreso.getFechaFin() != null) {
			ingreso.getFechaFin().setHours(0);
			ingreso.getFechaFin().setMinutes(0);
			ingreso.getFechaFin().setSeconds(0);
		}
		
		if (estilista == null && ingreso.getFecha() == null && ingreso.getFechaFin() == null) {
			this.ingresosX = null;
			attr.addFlashAttribute("warning", "No se ha encontrado ningun criterio de busqueda!");
			return "redirect:/ingreso/";
		}
		
		if ((estilista == null && ingreso.getFecha() == null && ingreso.getFechaFin() != null)
				|| (estilista != null && ingreso.getFecha() == null && ingreso.getFechaFin() != null)) {
			this.ingresosX = null;
			attr.addFlashAttribute("warning", "No se se puede filtar por los criterios seleccionados!");
			return "redirect:/ingreso/";
		}
		
		if (estilista != null && ingreso.getFecha() != null && ingreso.getFechaFin() == null) {
			this.ingresos = reciboService.buscarPorEstilistaAndFecha(estilista, ingreso.getFecha());
			this.ingresosX = this.ingresos;
		}
		
		if (estilista == null && ingreso.getFecha() != null && ingreso.getFechaFin() == null) {
			this.ingresos = reciboService.buscarPorFecha(ingreso.getFecha());
			this.ingresosX = this.ingresos;
		}
		
		if (estilista != null && ingreso.getFecha() == null && ingreso.getFechaFin() == null) {
			this.ingresos = reciboService.buscarPorEstilista(estilista);
			this.ingresosX = this.ingresos;
		} 
		
		if (estilista != null && ingreso.getFecha() != null && ingreso.getFechaFin() != null) {
			this.ingresos = reciboService.findByFechaBetweenAndEstilista(ingreso.getFecha(), ingreso.getFechaFin(), estilista);
			this.ingresosX = this.ingresos;
		} 
		
		if (estilista == null && ingreso.getFecha() != null && ingreso.getFechaFin() != null) {
			this.ingresos = reciboService.findByFechaBetween(ingreso.getFecha(), ingreso.getFechaFin());
			this.ingresosX = this.ingresos; 
		}
		
		if (this.ingresos.isEmpty()) {
			this.ingresosX = null;
			attr.addFlashAttribute("error", "No se ha encontrado ningun resultado!");
			return "redirect:/ingreso/";
		}
		
		model.addAttribute("ingresos", this.ingresos);
		model.addAttribute("ingreso", new ReciboPago());
		model.addAttribute("estilistas", estilistaService.listar());
		return "/Views/SI/Ingresos/ingresos";
	}
	
	@GetMapping("/limpiar")
	public String limpiarFiltro() {
		this.ingresosX = null;
		return "redirect:/ingreso/";
	}
	
	@GetMapping("/index")
	public String ingresoIndex(Model model) {
		
		if (!this.mode) {
			this.recibo = new ReciboPago();
			this.detallesAgregados = new ArrayList<>();
			this.productosServicios = new ArrayList<>();
			this.estilistas = new ArrayList<>();
			this.recibo.setTotal((long) 0);
			this.recibo.setNumeroRecibo("0");
			model.addAttribute("recibo", this.recibo);
			model.addAttribute("detalle", new DetallePago());
			model.addAttribute("prodsServs", this.productosServicios);
			model.addAttribute("detalles", this.detallesAgregados);
			model.addAttribute("mode", this.mode);
			return "/Views/SI/Ingresos/ingresoIndex";
		}
		
		long i = 1;
		for (DetallePago detallePago : detallesAgregados) {
			detallePago.setIdDetalle(i);
			i++;
		}
		
		model.addAttribute("recibo", this.recibo);
		model.addAttribute("prodsServs", this.productosServicios);
		model.addAttribute("detalle", new DetallePago());
		model.addAttribute("detalles", this.detallesAgregados);
		model.addAttribute("estilistas", this.estilistas);
		model.addAttribute("mode", this.mode);
		return "/Views/SI/Ingresos/ingresoIndex";     
	}
	
	@GetMapping("/preparar")
	public String preparar(RedirectAttributes attr) {
		this.mode = true;
		this.recibo = new ReciboPago();
		this.recibo.setTotal((long) 0);
		this.detallesAgregados = new ArrayList<>();
		this.productosServicios = servProdServ.listar();
		this.estilistas = estilistaService.listar();
		String numeroRecibo = "";
		
		if (reciboService.listar().isEmpty()) {
			numeroRecibo = "1";
			this.recibo.setNumeroRecibo(numeroRecibo);
			this.recibo.setTotal((long) 0);
			attr.addFlashAttribute("recibo", this.recibo);
			attr.addFlashAttribute("mode", this.mode);
			return "redirect:/ingreso/index";
		}
		
		ReciboPago reciboAux = reciboService.listar().get(reciboService.listar().size() - 1);
		Integer ultimoRecibo = Integer.parseInt(reciboAux.getNumeroRecibo());
		ultimoRecibo++;
		numeroRecibo = Integer.toString(ultimoRecibo);
		this.recibo.setNumeroRecibo(numeroRecibo);
		attr.addFlashAttribute("recibo", this.recibo);
		attr.addFlashAttribute("mode", this.mode);
		return "redirect:/ingreso/index";
	}
	
	@GetMapping("/cancelar")
	public String cancelar() {
		this.mode = false;
		return "redirect:/ingreso/index";
	}
	
	@GetMapping("/producto-servicio/details/{id}")
	public ResponseEntity<ProductoServicio> productoServicioDetail(@PathVariable("id") Long idProdServ) {
		try {
			return new ResponseEntity<ProductoServicio>(servProdServ.buscarPorId(idProdServ), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ProductoServicio>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/agregar-producto-servicio")
	public String agregarProductoServicio(@ModelAttribute DetallePago detalle) {
		ProductoServicio prodServ = servProdServ.buscarPorId(detalle.getProdServ().getIdProductoServ());
		
		detalle.setProdServ(prodServ);
		
		if (itemIsAdded(detalle) != null) {
			DetallePago detalleAux = itemIsAdded(detalle);
			Long indice = detalleAux.getIdDetalle();
			detalleAux.setCantidad(detalle.getCantidad() + detalleAux.getCantidad());
			detalleAux.setSubtotal(detalle.getProdServ().getPrecioVenta() * detalleAux.getCantidad());
			
			this.detallesAgregados.add(detalleAux);
			this.detallesAgregados.remove(Integer.parseInt(indice.toString()) - 1);
			
			recibo.setTotal(reciboService.calcularTotal(this.detallesAgregados));
			return "redirect:/ingreso/index";
		}
		
		detalle.setRecibo(this.recibo);
		detalle.setSubtotal(detalle.getCantidad() * prodServ.getPrecioVenta());
		this.detallesAgregados.add(detalle);
		recibo.setTotal(reciboService.calcularTotal(this.detallesAgregados));
		return "redirect:/ingreso/index";
	}
	
	public DetallePago itemIsAdded(DetallePago detalle) {
		for (DetallePago detallePago : detallesAgregados) {
			if (detallePago.getProdServ().getIdProductoServ().equals(detalle.getProdServ().getIdProductoServ())) {
				return detallePago;
			}
		}
		return null;
	}
	
	@GetMapping("/quitar-producto-servicio/{item}")
	public String quitarProductoServicio(@PathVariable("item") int item) {
		this.detallesAgregados.remove(item);
		recibo.setTotal(reciboService.calcularTotal(this.detallesAgregados));
		return "redirect:/ingreso/index";
	}
	
	@PostMapping("/agregar-estilista")
	public String agregarEstilista(@RequestParam("idEstilista") Long idEstilista, RedirectAttributes attr) {
		Estilista estilista = estilistaService.buscarPorId(idEstilista);
		
		this.recibo.setEstilista(estilista);
		return "redirect:/ingreso/index";
	}
	
	@PostMapping("/agregar-cliente")
	public String agregarCliente(@RequestParam("numeroDoc") String numeroDoc, RedirectAttributes attr) {
		Usuario usuario = usuarioService.buscarPorNumeroDoc(numeroDoc);
		
		if (usuario == null) {
			attr.addFlashAttribute("error", "No se ha encontrado ningun cliente con el numero ingresado!");
			return "redirect:/ingreso/index";
		}
		
		this.recibo.setUsuario(usuario);
		return "redirect:/ingreso/index";
	}
	
	@PostMapping("/agregar-cita")
	public String agregarCita(@RequestParam("numeroCita") Long numeroCita, RedirectAttributes attr) {
		Cita cita = citaService.buscarPorId(numeroCita);
		
		if (cita == null) {
			attr.addFlashAttribute("error", "No se ha encontrado ninguna cita con el numero ingresado!");
			return "redirect:/ingreso/index";
		} else if (!citaService.validOwnCita(this.recibo.getUsuario(), cita)) {
			attr.addFlashAttribute("error", "La cita seleccionada no coincide por la persona que la programo!");
			return "redirect:/ingreso/index";
		}
		
		this.recibo.setCita(cita);
		
		DetallePago detalle = new DetallePago();
		detalle.setRecibo(this.recibo);
		detalle.setProdServ(cita.getServicio());
		detalle.setCantidad(1);
		detalle.setSubtotal(detalle.getProdServ().getPrecioVenta() * detalle.getCantidad());
		
		agregarProductoServicio(detalle);
		return "redirect:/ingreso/index";
	}
	
	@PostMapping("/cobrar")
	public String cobrar(@RequestParam("efectivo") Long efectivo, RedirectAttributes attr, Model model) {
		if (efectivo < this.recibo.getTotal()) {
			attr.addFlashAttribute("error", "Cantidad insuficiente para cubrir el pago total!");
			return "redirect:/ingreso/index";
		} else if (this.recibo.getTotal() == 0 || this.detallesAgregados.isEmpty()) {
			attr.addFlashAttribute("error", "No se ha agregado ningun producto o servicio!");
			return "redirect:/ingreso/index";
		} else if (this.recibo.getEstilista() == null) {
			attr.addFlashAttribute("error", "No se ha agregado ningun estilista!");
			return "redirect:/ingreso/index";
		}
		
		this.recibo.setEfectivo(efectivo);
		this.recibo.setCambio(efectivo - this.recibo.getTotal());
		this.recibo.setHora(new Date());
		this.recibo.setFecha(new Date());
		
		if (this.recibo.getCita() != null) {
			Cita cita = this.recibo.getCita();
			cita.setEstado("FINALIZADA");
			citaService.guardar(cita);
		}
		
		for (DetallePago detallePago : detallesAgregados) {
			detallePago.setIdDetalle(null);
		}
		
		reciboService.guardar(recibo);
		detallePagoService.guardarLista(this.detallesAgregados);
		
		if (this.recibo.getUsuario() != null) {
			try {
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				
				String asunto = "SERVICIO BARBER X SYSTEM";
				String servicios = "<table>";
				servicios += "<thead>";
				servicios += "<tr>";
				servicios += "<th scope='col'>#</th>";
				servicios += "<th scope='col'>Producto - Servicio</th>";
				servicios += "<th scope='col'>Cantidad</th>";
				servicios += "</tr>";
				servicios += "<tr><th><hr></th><th><hr></th><th><hr></th></tr>";
				servicios += "</thead>";
				servicios += "<tbody>";
				//correos.toArray(String[]::new);
				for (DetallePago detalle : this.detallesAgregados) {
					servicios += "<tr>";
					servicios += "<th>"+detalle.getIdDetalle()+"</th>";
					servicios += "<th>"+detalle.getProdServ().getNombre()+"</th>";
					servicios += "<th>"+detalle.getCantidad()+"</th>";
					servicios += "</tr>";
				}
				
				servicios += "</tbody>";
				servicios += "</table>";
				
				String mailContent = "<p><b>ASUNTO: </b>SERVICIO BARBER X SYSTEM</p>";
				mailContent += "<p><b>ESTILISTA: </b>"+this.recibo.getEstilista().getUsuario().getNombres()+" "+this.recibo.getEstilista().getUsuario().getApellidos()+"</p>";
				mailContent += "<p><b>EMPRESA: </b>'Black and White'</p>";
				mailContent += "<p><b>DIRECCION: </b>Av Calle 26 # 14-60 - Bogotá</p>";
				mailContent += "<p><b>TELEFONO: </b>7777777</p>";
				mailContent += servicios;
				
				helper.setTo(this.recibo.getUsuario().getEmail());
				helper.setSubject(asunto);
				helper.setText(mailContent, true);
				
				mailSender.send(message);
			} catch (Exception e) {
				model.addAttribute("recibo", this.recibo);
				model.addAttribute("prodsServs", this.productosServicios);
				model.addAttribute("detalle", new DetallePago());
				model.addAttribute("detalles", this.detallesAgregados);
				model.addAttribute("estilistas", this.estilistas);
				model.addAttribute("mode", this.mode);
				return "/Views/SI/Ingresos/ingresoIndex";
			}
		}
		
		this.mode = false;
		return "redirect:/ingreso/cobrar/success";
	}
	
	@GetMapping("/cobrar/success")
	public String cobrarSuccess(Model model) {
		if (this.recibo == null || this.detallesAgregados.isEmpty() || this.recibo.getEfectivo() == 0) {
			return "redirect:/ingreso/index";
		}
		model.addAttribute("recibo", this.recibo);
		return "/Views/SI/Ingresos/pagoSuccess";
	}
	
	@GetMapping("/detalles/{id}")
	public String detalles(@PathVariable("id") Long idRecibo, Model model, RedirectAttributes attr) {
		ReciboPago reciboPago = reciboService.buscarPorId(idRecibo);
		
		if (reciboPago == null) {
			attr.addFlashAttribute("error", "El ID del recibo que ingreso no existe!");
			return "redirect:/ingreso/";
		}
		
		model.addAttribute("detalles", detallePagoService.buscarPorRecibo(reciboPago));
		return "/Views/SI/Ingresos/detallesIngresos";
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/diario")
	public String ingresoDiario(Model model) {
		Date fechaActual = new Date();
		
		fechaActual.setHours(0);
		fechaActual.setMinutes(0);
		fechaActual.setSeconds(0);
		
		List<ReciboPago> ingresosDiarios = reciboService.buscarPorFecha(fechaActual);
		int total = 0;
		int servicios = 0;
		int productos = 0;
		
		for (ReciboPago reciboPago : ingresosDiarios) {
			total += reciboPago.getTotal();
			
			List<DetallePago> detalles = detallePagoService.buscarPorRecibo(reciboPago);
			
			for (DetallePago detalle : detalles) {
				if (detalle.getProdServ().getCategoria().equals("PRODUCTO")) {
					productos += detalle.getSubtotal();
				} else if (detalle.getProdServ().getCategoria().equals("SERVICIO")) {
					servicios += detalle.getSubtotal();
				}
			}
		}
		
		model.addAttribute("servicios", servicios);
		model.addAttribute("productos", productos);
		model.addAttribute("total", total);
		model.addAttribute("ingresosDiarios", ingresosDiarios);
		return "/Views/SI/Ingresos/ingresoDiario";
	}

	@GetMapping("/historial/cliente")
	public String historialCliente() {
		return "/Views/SI/Citas/historialCliente";
	}
	
	@PostMapping("/historial/cliente")
	public String historialCliente(@RequestParam("numeroDoc") String numeroDoc, Model model) {
		Usuario user = usuarioService.buscarPorNumeroDoc(numeroDoc);
		List<Cita> citasAux = new ArrayList<>();
		
		if (user == null) {
			model.addAttribute("error", "No se encontro ningun cliente!");
			return "/Views/SI/Citas/historialCliente";
		}
		
		citasAux = citaService.buscarPorUsuario(user); 
		
		if (citasAux.isEmpty()) {
			model.addAttribute("error", "No se encontraron resulatdos!");
			return "/Views/SI/Citas/historialCliente";
		}
		
		model.addAttribute("citas", citasAux);
		return "/Views/SI/Citas/historialCliente";
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=\"ingresos.xlsx\"");
		Sheet hoja = workbook.createSheet("Ingresos");
		
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
		String[] columnas = { "ID", "Cedula cliente", "Cliente", "Estilista", "Fecha", "Hora", "Total"};

		for (int i = 0; i < columnas.length; i++) {
			celda = filaData.createCell(i);
			celda.setCellValue(columnas[i]);
			celda.setCellStyle(style);
		}

		int numFila = 1;

		if (this.ingresosX == null) {
			this.ingresosX = reciboService.listar();
		}

		for (ReciboPago ingreso : this.ingresosX) {
			filaData = hoja.createRow(numFila);

			filaData.createCell(0).setCellValue(ingreso.getIdRecibo());
			hoja.autoSizeColumn(0);
			
			if (ingreso.getUsuario() == null) {
				filaData.createCell(1).setCellValue("SIN CLIENTE");
				hoja.autoSizeColumn(1);
				filaData.createCell(2).setCellValue("SIN CLIENTE");
				hoja.autoSizeColumn(2);
			} else {
				filaData.createCell(1).setCellValue(ingreso.getUsuario().getNumeroDoc());
				hoja.autoSizeColumn(1);
				filaData.createCell(2).setCellValue(ingreso.getUsuario().getNombres()+" "+ingreso.getUsuario().getApellidos());
				hoja.autoSizeColumn(2);
			}
			
			filaData.createCell(3).setCellValue(ingreso.getEstilista().getUsuario().getNombres()+" "+ingreso.getEstilista().getUsuario().getApellidos());
			hoja.autoSizeColumn(3);
			String fecha = ingreso.getFecha().getDate()+"-"+(ingreso.getFecha().getMonth()+1)+"-"+(ingreso.getFecha().getYear()+1900);
			filaData.createCell(4).setCellValue(fecha);
			hoja.autoSizeColumn(4);
			String hora = ingreso.getHora().getHours()+":"+ingreso.getHora().getMinutes();
			filaData.createCell(5).setCellValue(hora);
			hoja.autoSizeColumn(5);
			filaData.createCell(6).setCellValue("$"+ingreso.getTotal());
			hoja.autoSizeColumn(6);
			numFila++;
		}
	}
	
}
