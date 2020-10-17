package com.barber_x_system.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barber_x_system.entity.DetallePago;
import com.barber_x_system.repository.DetallePagoRepository;
import com.barber_x_system.service.IDetallePagoServ;

@Service
public class DetallePagoServImp implements IDetallePagoServ{
	
	@Autowired
	private DetallePagoRepository detalleRepo;

	@Override
	public List<DetallePago> listar() {
		return (List<DetallePago>) detalleRepo.findAll();
	}

	@Override
	public void guardar(DetallePago detalle) {
		detalleRepo.save(detalle);
	}

	@Override
	public void eliminar(Long idDetalle) {
		detalleRepo.deleteById(idDetalle);
	}

	@Override
	public DetallePago buscarPorId(Long idDetalle) {
		return detalleRepo.findById(idDetalle).orElse(null);
	}

}
