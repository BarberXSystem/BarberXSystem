package com.barber_x_system.service;

import java.util.List;
import com.barber_x_system.entity.Estilista;

public interface IEstilistaServ {
	
	//METODOS CRUD
	public List<Estilista> listar();
	public void guardar(Estilista estilista);
	public void eliminar(Long idEstilista);
	
	//METODOS AUXILIARES
	public Estilista buscarPorId(Long idEstilista);
}
