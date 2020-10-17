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
@Table(name = "detalles_pago")
public class DetallePago implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_detalle")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDetalle;
	
	@ManyToOne
	@JoinColumn(name = "id_recibo")
	private ReciboPago recibo;
	
	@ManyToOne
	@JoinColumn(name = "id_producto_servicio")
	private ProductoServicio prodServ;
	
	private Integer cantidad;
	
	private Long subtotal;
	
	public DetallePago() {
		
	}

	public Long getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Long idDetalle) {
		this.idDetalle = idDetalle;
	}

	public ReciboPago getRecibo() {
		return recibo;
	}

	public void setRecibo(ReciboPago recibo) {
		this.recibo = recibo;
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

	public Long getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Long subtotal) {
		this.subtotal = subtotal;
	}

	@Override
	public String toString() {
		return "DetallePago [idDetalle=" + idDetalle + ", recibo=" + recibo + ", prodServ=" + prodServ + ", cantidad="
				+ cantidad + ", subtotal=" + subtotal + "]";
	}

}
