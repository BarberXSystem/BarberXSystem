package com.barber_x_system.service;

import java.util.List;
import com.barber_x_system.entity.Administrador;

public interface IAdminServ {
	
	//METODOS CRUD
	public List<Administrador> listar();
	public void guardar(Administrador admin);
	public void eliminar(Long idAdmin);
	
	//METODOS AUXILIARES
	public Administrador buscarPorId(Long idAdmin);

}
