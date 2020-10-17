package com.barber_x_system.imp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.barber_x_system.entity.DetalleCita;
import com.barber_x_system.repository.DetalleCitaRepository;
import com.barber_x_system.service.IDetalleCitaServ;

@Service
public class DetalleCitaServImp implements IDetalleCitaServ{
	
	@Autowired
	private DetalleCitaRepository detalleRepo;

	@Override
	public List<DetalleCita> listar() {
		return (List<DetalleCita>) detalleRepo.findAll();
	}

	@Override
	public void guardar(DetalleCita detalle) {
		detalleRepo.save(detalle);
	}

	@Override
	public void eliminar(Long idDetalle) {
		detalleRepo.deleteById(idDetalle);
	}

	@Override
	public DetalleCita buscarPorId(Long idDetalle) {
		return detalleRepo.findById(idDetalle).orElse(null);
	}

}
