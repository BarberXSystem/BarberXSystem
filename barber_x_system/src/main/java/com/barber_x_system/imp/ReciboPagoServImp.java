package com.barber_x_system.imp;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barber_x_system.entity.DetallePago;
import com.barber_x_system.entity.Estilista;
import com.barber_x_system.entity.ReciboPago;
import com.barber_x_system.repository.ReciboPagoRepository;
import com.barber_x_system.service.IReciboPagoServ;

@Service
public class ReciboPagoServImp implements IReciboPagoServ{
	
	@Autowired
	private ReciboPagoRepository  reciboRepo;

	@Override
	public List<ReciboPago> listar() {
		return (List<ReciboPago>) reciboRepo.findAll();
	}

	@Override
	public void guardar(ReciboPago recibo) {
		reciboRepo.save(recibo);
	}

	@Override
	public void eliminar(Long idRecibo) {
		reciboRepo.deleteById(idRecibo);
	}

	@Override
	public ReciboPago buscarPorId(Long idRecibo) {
		return reciboRepo.findById(idRecibo).orElse(null);
	}

	@Override
	public Long calcularTotal(List<DetallePago> detalles) {
		long total = 0;
		
		for (DetallePago detallePago : detalles) {
			total += detallePago.getSubtotal();
		}
		
		return total;
	}

	@Override
	public List<ReciboPago> buscarPorFecha(Date fecha) {
		return reciboRepo.findByFecha(fecha);
	}

	@Override
	public List<ReciboPago> findByFechaBetween(Date fechaInicio, Date fechaFin) {
		return reciboRepo.findByFechaBetween(fechaInicio, fechaFin);
	}

	@Override
	public List<ReciboPago> findByFechaBetweenAndEstilista(Date fechaInicio, Date fechaFin, Estilista estilista) {
		return reciboRepo.findByFechaBetweenAndEstilista(fechaInicio, fechaFin, estilista);
	}

	@Override
	public List<ReciboPago> buscarPorEstilista(Estilista estilista) {
		return reciboRepo.findByEstilista(estilista);
	}

	@Override
	public List<ReciboPago> buscarPorEstilistaAndFecha(Estilista estilista, Date fecha) {
		return reciboRepo.findByEstilistaAndFecha(estilista, fecha);
	}

}
