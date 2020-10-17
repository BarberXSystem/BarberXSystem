package com.barber_x_system.service;

import java.util.List;
import com.barber_x_system.entity.ReciboPago;

public interface IReciboPago {

	//METODOS CRUD
	public List<ReciboPago> listar();
	public void guardar(ReciboPago recibo);
	public void eliminar(Long idRecibo);
	
	//METODOS AUXILIARES
	public ReciboPago buscarPorId(Long idRecibo);
	
}
