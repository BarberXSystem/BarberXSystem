package com.barber_x_system.service;

import java.util.List;
import com.barber_x_system.entity.DetallePago;
import com.barber_x_system.entity.ReciboPago;

public interface IDetallePagoServ {
	
	//METODOS CRUD
	public List<DetallePago> listar();
	public void guardar(DetallePago detalle);
	public void guardarLista(List<DetallePago> detalles);
	public void eliminar(Long idDetalle);
	
	//METODOS AUXILIARES
	public DetallePago buscarPorId(Long idDetalle);
	public List<DetallePago> buscarPorRecibo(ReciboPago recibo);

}
