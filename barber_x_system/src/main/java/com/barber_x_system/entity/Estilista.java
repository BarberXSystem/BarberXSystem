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
@Table(name = "estilistas")
public class Estilista implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_estilista")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEstilista;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	public Estilista() {
		
	}

	public Long getIdEstilista() {
		return idEstilista;
	}

	public void setIdEstilista(Long idEstilista) {
		this.idEstilista = idEstilista;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Estilista [idEstilista=" + idEstilista + ", usuario=" + usuario + "]";
	}

}
