package com.barber_x_system.service;

import java.util.List;
import com.barber_x_system.entity.ProductoServicio;

public interface IProductoServicioServ {

	//METODOS CRUD
	public List<ProductoServicio> listar();
	public void guardar(ProductoServicio productoServ);
	public void eliminar(Long idProductoServ);
	
	//METODOS AUXILIARES
	public ProductoServicio buscarPorId(Long idProductoServ);
	public boolean existePorNombre(String nombre);
	public List<ProductoServicio> buscarProductos();
	public List<ProductoServicio> buscarServicios();
}
