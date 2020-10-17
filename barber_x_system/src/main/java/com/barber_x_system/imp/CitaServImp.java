package com.barber_x_system.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barber_x_system.entity.Cita;
import com.barber_x_system.repository.CitaRepository;
import com.barber_x_system.service.ICitaServ;

@Service
public class CitaServImp implements ICitaServ{
	
	@Autowired
	private CitaRepository citaRepo;

	@Override
	public List<Cita> listar() {
		return (List<Cita>) citaRepo.findAll();
	}

	@Override
	public void guardar(Cita cita) {
		citaRepo.save(cita);
	}

	@Override
	public void eliminar(Long idCita) {
		citaRepo.deleteById(idCita);
	}

	@Override
	public Cita buscarPorId(Long idCita) {
		return citaRepo.findById(idCita).orElse(null);
	}

}
