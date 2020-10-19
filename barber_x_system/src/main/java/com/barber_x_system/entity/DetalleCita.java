package com.barber_x_system.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "detalles_cita")
public class DetalleCita implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_detalle")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDetalle;
	
	@ManyToOne
	@JoinColumn(name = "id_cita")
	private Cita cita;
	
	@ManyToOne
	@JoinColumn(name = "id_producto_servicio")
	private ProductoServicio prodServ;
	
	private Integer cantidad;

	public Long getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Long idDetalle) {
		this.idDetalle = idDetalle;
	}

	public Cita getCita() {
		return cita;
	}

	public void setCita(Cita cita) {
		this.cita = cita;
	}

	public ProductoServicio getProdServ() {
		return prodServ;
	}

	public void setProdServ(ProductoServicio prodServ) {
		this.prodServ = prodServ;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "DetalleCita [idDetalle=" + idDetalle + ", cita=" + cita + ", prodServ=" + prodServ + ", cantidad="
				+ cantidad + "]";
	}
	
}
