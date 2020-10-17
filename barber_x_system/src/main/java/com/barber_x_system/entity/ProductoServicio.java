package com.barber_x_system.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity(name = "productos_servicios")
public class ProductoServicio implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_producto_servicio")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProductoServ;
	
	private String nombre;
	
	private String descripcion;
	
	@Column(name = "precio_compra")
	private Long precioCompra;
	
	@Column(name = "precio_venta")
	private Long precioVenta;
	
	private String categoria;
	
	public ProductoServicio() {
		
	}

	public Long getIdProductoServ() {
		return idProductoServ;
	}

	public void setIdProductoServ(Long idProductoServ) {
		this.idProductoServ = idProductoServ;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(Long precioCompra) {
		this.precioCompra = precioCompra;
	}

	public Long getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Long precioVenta) {
		this.precioVenta = precioVenta;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "ProductoServicio [idProductoServ=" + idProductoServ + ", nombre=" + nombre + ", descripcion="
				+ descripcion + ", precioCompra=" + precioCompra + ", precioVenta=" + precioVenta + ", categoria="
				+ categoria + "]";
	}

}
