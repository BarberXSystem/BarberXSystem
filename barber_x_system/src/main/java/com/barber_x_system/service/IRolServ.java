package com.barber_x_system.service;

import java.util.List;
import com.barber_x_system.entity.Rol;

public interface IRolServ {
	
	//METODOS CRUD
	public List<Rol> listar();
	public void guardar(Rol rol);
	public void eliminar(Long idRol);
	
	//METODOS AUXILIARES
	public Rol buscarPorId(Long idRol);

}
