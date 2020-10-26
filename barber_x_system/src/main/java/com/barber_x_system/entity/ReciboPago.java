package com.barber_x_system.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "recibo_pago")
public class ReciboPago implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_recibo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRecibo;
	
	@ManyToOne
	@JoinColumn(name = "id_cita")
	private Cita cita;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_estilista")
	private Estilista estilista;
	
	@Column(name = "numero_recibo")
	private String numeroRecibo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;

	@DateTimeFormat(pattern = "HH:mm")
	private Date hora;
	
	private Long total;
	
	private Long efectivo;
	
	private Long cambio;
	
	public ReciboPago() {
		
	}

	public Long getIdRecibo() {
		return idRecibo;
	}

	public void setIdRecibo(Long idRecibo) {
		this.idRecibo = idRecibo;
	}

	public Cita getCita() {
		return cita;
	}

	public void setCita(Cita cita) {
		this.cita = cita;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Estilista getEstilista() {
		return estilista;
	}

	public void setEstilista(Estilista estilista) {
		this.estilista = estilista;
	}

	public String getNumeroRecibo() {
		return numeroRecibo;
	}

	public void setNumeroRecibo(String numeroRecibo) {
		this.numeroRecibo = numeroRecibo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getEfectivo() {
		return efectivo;
	}

	public void setEfectivo(Long efectivo) {
		this.efectivo = efectivo;
	}

	public Long getCambio() {
		return cambio;
	}

	public void setCambio(Long cambio) {
		this.cambio = cambio;
	}

	@Override
	public String toString() {
		return "ReciboPago [idRecibo=" + idRecibo + ", cita=" + cita + ", usuario=" + usuario + ", estilista="
				+ estilista + ", numeroRecibo=" + numeroRecibo + ", fecha=" + fecha + ", hora=" + hora + ", total="
				+ total + ", efectivo=" + efectivo + ", cambio=" + cambio + "]";
	}
	
}
