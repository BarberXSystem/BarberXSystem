package com.barber_x_system.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barber_x_system.entity.Estilista;
import com.barber_x_system.repository.EstilistaRepository;
import com.barber_x_system.service.IEstilistaServ;

@Service
public class EstilistaServImp implements IEstilistaServ{
	
	@Autowired
	private EstilistaRepository estilistaRepo;

	@Override
	public List<Estilista> listar() {
		return (List<Estilista>) estilistaRepo.findAll();
	}

	@Override
	public void guardar(Estilista estilista) {
		estilistaRepo.save(estilista);
	}

	@Override
	public void eliminar(Long idEstilista) {
		estilistaRepo.deleteById(idEstilista);
	}

	@Override
	public Estilista buscarPorId(Long idEstilista) {
		return estilistaRepo.findById(idEstilista).orElse(null);
	}

}
