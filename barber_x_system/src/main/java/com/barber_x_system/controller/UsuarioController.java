package com.barber_x_system.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import com.barber_x_system.entity.Usuario;
import com.barber_x_system.service.IUsuarioServ;

@Controller
@RequestMapping("/usuario")
@Component("/Views/SI/Usuario/usuarios.xlsx")
public class UsuarioController extends AbstractXlsxView{
	
	@Autowired
	private IUsuarioServ usuarioServ;
	
	private List<Usuario> usuarios = new ArrayList<>();
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/")
	public String listar(Model model) {
		this.usuarios = usuarioServ.listar();
		model.addAttribute("usuarios", this.usuarios);
		model.addAttribute("usuario", new Usuario());
		return "/Views/SI/Usuario/usuarios";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/nuevo")
	public String registroUsuario(@ModelAttribute Usuario usuario, Model model, RedirectAttributes attr) {
		if (usuarioServ.existePorNumeroDoc(usuario.getNumeroDoc())) {
			model.addAttribute("error", "El numero de documento ya se encuentra registrado!");
			model.addAttribute("usuarios", usuarioServ.listar());
			model.addAttribute("usuario", usuario);
			return "/Views/SI/Usuario/usuarios";
		} else if (usuarioServ.existePorEmail(usuario.getEmail())) {
			model.addAttribute("error", "El correo electronico ingresado ya se encuentra registrado!");
			model.addAttribute("usuarios", usuarioServ.listar());
			model.addAttribute("usuario", usuario);
			return "/Views/SI/Usuario/usuarios";
		} else if (!usuarioServ.passMatch(usuario)) {
			model.addAttribute("error", "Las contraseñas ingresadas no coinciden!");
			model.addAttribute("usuarios", usuarioServ.listar());
			model.addAttribute("usuario", usuario);
			return "/Views/SI/Usuario/usuarios";
		}
		usuario.setEnabled(true);
		usuarioServ.guardar(usuario);
		attr.addFlashAttribute("success", "Usuario guardado correctamente!");
		return "redirect:/usuario/";	
	}
	
	@Secured("ROLE_ADMIN")
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
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/editar")
	public String editar(@ModelAttribute Usuario usuario, RedirectAttributes attr, Model model) {
		if (usuarioServ.existePorEmail(usuario.getEmail())) {
			model.addAttribute("error", "El correo electronico ingresado ya se encuentra registrado!");
			model.addAttribute("usuarios", usuarioServ.listar());
			model.addAttribute("usuario", usuario);
			return "/Views/SI/Usuario/editarUsuario";
		}
		usuarioServ.guardar(usuario);
		attr.addFlashAttribute("success", "Usuario actualizado correctamente!");
		return "redirect:/usuario/";
	}
	
	@Secured("ROLE_ADMIN")
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
	
	@Secured("ROLE_USER")
	@GetMapping("/editar-perfil")
	public String editarPerfil(Principal principal, Model model) {
		Usuario usuarioLocal = usuarioServ.buscarPorNumeroDoc(principal.getName());
		model.addAttribute("usuario", usuarioLocal);
		return "/Views/SI/Perfil/editarPerfil";
	}
	
	@Secured("ROLE_USER")
	@PostMapping("/editar-perfil")
	public String editarPerfil(@ModelAttribute Usuario usuario, RedirectAttributes attr, Model model) {
		Usuario usuarioAux = usuarioServ.buscarPorId(usuario.getIdUsuario());
		
		if (usuarioServ.existePorEmail(usuario.getEmail()) && !usuario.getEmail().toLowerCase().equals(usuarioAux.getEmail().toLowerCase())) {
			model.addAttribute("error", "El correo ingresado ya esta siendo utilizado por otra persona!");
			model.addAttribute("usuario", usuario);
			return "/Views/SI/Perfil/editarPerfil";
		}
		
		usuarioServ.guardar(usuario);
		attr.addFlashAttribute("success", "Información guardada correctamente!");
		return "redirect:/dashboard/";
	}
	
	@Secured("ROLE_USER")
	@PostMapping("/cambiar-password")
	public String cambiarPassword(@ModelAttribute Usuario usuario, RedirectAttributes attr, Model model) {		
		if (!usuario.getPassword().equals(usuario.getConfirmPassword())) {
			model.addAttribute("error", "Las contraseñas no coinciden!");
			model.addAttribute("usuario", usuario);
			return "/Views/SI/Perfil/editarPerfil";
		}
		
		usuarioServ.guardar(usuario);
		attr.addFlashAttribute("success", "Información guardada correctamente!");
		return "redirect:/dashboard/";
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			response.setHeader("Content-Disposition", "attachment; filename=\"clientes.xlsx\"");
			Sheet hoja = workbook.createSheet("Clientes");
			
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
			String[] columnas = { "ID", "Cedula cliente", "Nombres", "Apellidos", "Direccion",
					"Telefono", "Email"};

			for (int i = 0; i < columnas.length; i++) {
				celda = filaData.createCell(i);
				celda.setCellValue(columnas[i]);
				celda.setCellStyle(style);
			}

			int numFila = 1;

			for (Usuario usuario : this.usuarios) {
				filaData = hoja.createRow(numFila);

				filaData.createCell(0).setCellValue(usuario.getIdUsuario());
				hoja.autoSizeColumn(0);
				filaData.createCell(1).setCellValue(usuario.getNumeroDoc());
				hoja.autoSizeColumn(1);
				filaData.createCell(2).setCellValue(usuario.getNombres());
				hoja.autoSizeColumn(2);
				filaData.createCell(3).setCellValue(usuario.getApellidos());
				hoja.autoSizeColumn(3);
				filaData.createCell(4).setCellValue(usuario.getDireccion());
				hoja.autoSizeColumn(4);
				filaData.createCell(5).setCellValue(usuario.getTelefono());
				hoja.autoSizeColumn(5);
				filaData.createCell(6).setCellValue(usuario.getEmail());
				hoja.autoSizeColumn(6);
				numFila++;
			}
		
	}

}
