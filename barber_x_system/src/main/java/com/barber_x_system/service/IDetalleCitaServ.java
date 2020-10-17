package com.barber_x_system.service;

import java.util.List;
import com.barber_x_system.entity.DetalleCita;

public interface IDetalleCitaServ {

	//METODOS CRUD
	public List<DetalleCita> listar();
	public void guardar(DetalleCita detalle);
	public void eliminar(Long idDetalle);
	
	//METODOS AUXILIARES
	public DetalleCita buscarPorId(Long idDetalle);
}
