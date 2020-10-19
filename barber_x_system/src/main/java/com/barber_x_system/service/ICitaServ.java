package com.barber_x_system.service;

import java.util.Date;
import java.util.List;
import com.barber_x_system.entity.Cita;
import com.barber_x_system.entity.Estilista;
import com.barber_x_system.entity.Usuario;

public interface ICitaServ {
	
	//METODOS CRUD
	public List<Cita> listar();
	public void guardar(Cita cita);
	public void eliminar(Long idCita);
	
	//METODOS AUXILIARES
	public Cita buscarPorId(Long idCita);
	public List<Cita> buscarPorUsuario(Usuario usuario);
	public List<Cita> buscarPorFechaAndEstilista(Date fecha, Estilista estilista);
	
	//METODOS LOGICOS DEL SISTEMA
	public List<String> turnosDisponibles(Date fecha, Estilista estilista);
}
