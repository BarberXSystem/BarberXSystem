package com.barber_x_system.imp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barber_x_system.entity.DetallePago;
import com.barber_x_system.entity.ReciboPago;
import com.barber_x_system.repository.ReciboPagoRepository;
import com.barber_x_system.service.IReciboPago;

@Service
public class ReciboPagoServImp implements IReciboPago{
	
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

}
