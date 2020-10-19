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
@Table(name = "citas")
public class Cita implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_cita")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCita;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_estilista")
	private Estilista estilista;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	
	private String hora;
	
	private String estado;
	
	public Cita() {
		
	}

	public Long getIdCita() {
		return idCita;
	}

	public void setIdCita(Long idCita) {
		this.idCita = idCita;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Cita [idCita=" + idCita + ", usuario=" + usuario + ", estilista=" + estilista + ", fecha=" + fecha
				+ ", hora=" + hora + ", estado=" + estado + "]";
	}

}
