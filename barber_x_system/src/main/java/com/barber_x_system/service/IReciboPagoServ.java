package com.barber_x_system.service;

import java.util.Date;
import java.util.List;
import com.barber_x_system.entity.DetallePago;
import com.barber_x_system.entity.ReciboPago;

public interface IReciboPagoServ {

	//METODOS CRUD
	public List<ReciboPago> listar();
	public void guardar(ReciboPago recibo);
	public void eliminar(Long idRecibo);
	
	//METODOS AUXILIARES
	public ReciboPago buscarPorId(Long idRecibo);
	public List<ReciboPago> buscarPorFecha(Date fecha);
	
	//METODOS LOGICOS DEL SISTEMA
	public Long calcularTotal(List<DetallePago> detalles);
	
}
