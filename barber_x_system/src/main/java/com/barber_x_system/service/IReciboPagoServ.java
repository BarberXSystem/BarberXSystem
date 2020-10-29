package com.barber_x_system.service;

import java.util.Date;
import java.util.List;
import com.barber_x_system.entity.DetallePago;
import com.barber_x_system.entity.Estilista;
import com.barber_x_system.entity.ReciboPago;

public interface IReciboPagoServ {

	//METODOS CRUD
	public List<ReciboPago> listar();
	public void guardar(ReciboPago recibo);
	public void eliminar(Long idRecibo);
	
	//METODOS AUXILIARES
	public ReciboPago buscarPorId(Long idRecibo);
	public List<ReciboPago> buscarPorFecha(Date fecha);
	public List<ReciboPago> buscarPorEstilista(Estilista estilista);
	public List<ReciboPago> buscarPorEstilistaAndFecha(Estilista estilista, Date fecha);
	public List<ReciboPago> findByFechaBetween(Date fechaInicio, Date fechaFin);
	public List<ReciboPago> findByFechaBetweenAndEstilista(Date fechaInicio, Date fechaFin, Estilista estilista);
	
	//METODOS LOGICOS DEL SISTEMA
	public Long calcularTotal(List<DetallePago> detalles);
	
}
