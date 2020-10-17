package com.barber_x_system.service;

import java.util.List;
import com.barber_x_system.entity.Usuario;

public interface IUsuarioServ {
	
	//METODOS CRUD
	public List<Usuario> listar();
	public void guardar(Usuario usuario);
	public void eliminar(Long idUsuario);
	
	//METODOS AUXILIARES
	public Usuario buscarPorId(Long idUsuario);

}
