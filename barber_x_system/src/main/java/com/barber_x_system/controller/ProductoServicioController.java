package com.barber_x_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.barber_x_system.entity.ProductoServicio;
import com.barber_x_system.service.IProductoServicioServ;

@Controller
public class ProductoServicioController {
	
	@Autowired
	private IProductoServicioServ prodServService;
	
	@GetMapping("/producto/")
	public String listarProductos(Model model) {
		model.addAttribute("producto", new ProductoServicio());
		model.addAttribute("productos", prodServService.buscarProductos());
		return "/Views/SI/Producto/productos";
	}
	
	@PostMapping("/producto/nuevo")
	public String nuevoProducto(@ModelAttribute ProductoServicio producto, RedirectAttributes attr,
			Model model) {
		
		if (prodServService.existePorNombre(producto.getNombre())) {
			model.addAttribute("producto", producto);
			model.addAttribute("productos", prodServService.buscarProductos());
			model.addAttribute("error", "El nombre del producto ingresado ya existe!");
			return "/Views/SI/Producto/productos";
		}
		producto.setCategoria("PRODUCTO");
		prodServService.guardar(producto);
		attr.addFlashAttribute("success", "Producto guardado correctamente!");
		return "redirect:/producto/";
	}
	
	@GetMapping("/servicio/")
	public String listarServicios(Model model) {
		model.addAttribute("servicio", new ProductoServicio());
		model.addAttribute("servicios", prodServService.buscarServicios());
		return "/Views/SI/Servicios/servicios";
	}
	
	@PostMapping("/servicio/nuevo")
	public String nuevoServicio(@ModelAttribute ProductoServicio servicio, RedirectAttributes attr,
			Model model) {
		
		if (prodServService.existePorNombre(servicio.getNombre())) {
			model.addAttribute("servicio", servicio);
			model.addAttribute("servicios", prodServService.buscarServicios());
			model.addAttribute("error", "El nombre del servicio ingresado ya existe!");
			return "/Views/SI/Servicios/servicios";
		}
		servicio.setCategoria("SERVICIO");
		prodServService.guardar(servicio);
		attr.addFlashAttribute("success", "Servicio guardado correctamente!");
		return "redirect:/servicio/";
	}
	
	@GetMapping("/producto-servicio/editar/{id}")
	public String editar(@PathVariable("id") Long idProdServ, Model model, RedirectAttributes attr) {
		ProductoServicio prodServ = null;
		
		if (idProdServ > 0) {
			prodServ = prodServService.buscarPorId(idProdServ);	
			if (prodServ == null) {
				attr.addFlashAttribute("error", "El ID del producto o servicio que desea editar no existe!");
				return "redirect:/producto/";
			}
			
		} else {
			attr.addFlashAttribute("error", "El ID del producto o servicio que desea editar no existe!");
			return "redirect:/producto/";
		}
		
		model.addAttribute("prodServ", prodServ);
		return "/Views/SI/ProductoServicio/editarProdServ";
	}
	
	@PostMapping("/producto-servicio/editar")
	public String editarProdServ(@ModelAttribute ProductoServicio prodServ, RedirectAttributes attr) {
	
		if (prodServ.getCategoria().equals("PRODUCTO")) {
			prodServService.guardar(prodServ);
			attr.addFlashAttribute("success", "Producto actualizado correctamente!");
			return "redirect:/producto/";
		}
		prodServService.guardar(prodServ);
		attr.addFlashAttribute("success", "Servicio actualizado correctamente!");
		return "redirect:/servicio/";
	}
	
	@GetMapping("/producto-servicio/eliminar/{id}")
	public String eliminarProdServ(@PathVariable("id") Long idProdServ, RedirectAttributes attr, Model model) {
		ProductoServicio prodServ = null;
		
		if (idProdServ > 0) {
			prodServ = prodServService.buscarPorId(idProdServ);	
			if (prodServ == null) {
				attr.addFlashAttribute("error", "El ID del producto o servicio que desea eliminar no existe!");
				return "redirect:/producto/";
			}
			
		} else {
			attr.addFlashAttribute("error", "El ID del producto o servicio que desea eliminar no existe!");
			return "redirect:/producto/";
		}
		
		if (prodServ.getCategoria().equals("PRODUCTO")) {
			prodServService.eliminar(idProdServ);
			attr.addFlashAttribute("warning", "Producto eliminado correctamente!");
			return "redirect:/producto/";
		}
		
		prodServService.eliminar(idProdServ);
		attr.addFlashAttribute("warning", "Servicio eliminado correctamente!");
		return "redirect:/servicio/";
	}

}
