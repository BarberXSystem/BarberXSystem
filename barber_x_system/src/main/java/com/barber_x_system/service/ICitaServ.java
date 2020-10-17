package com.barber_x_system.service;

import java.util.List;
import com.barber_x_system.entity.Cita;

public interface ICitaServ {
	
	//METODOS CRUD
	public List<Cita> listar();
	public void guardar(Cita cita);
	public void eliminar(Long idCita);
	
	//METODOS AUXILIARES
	public Cita buscarPorId(Long idCita);
}
