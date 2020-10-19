package com.barber_x_system.imp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.barber_x_system.entity.Rol;
import com.barber_x_system.entity.Usuario;
import com.barber_x_system.repository.RolRepository;
import com.barber_x_system.service.IRolServ;

@Service
public class RolServImp implements IRolServ{
	
	@Autowired
	private RolRepository rolRepo;

	@Override
	public List<Rol> listar() {
		return (List<Rol>) rolRepo.findAll();
	}

	@Override
	public void guardar(Rol rol) {
		rolRepo.save(rol);
	}

	@Override
	public void eliminar(Long idRol) {
		rolRepo.deleteById(idRol);
	}

	@Override
	public Rol buscarPorId(Long idRol) {
		return rolRepo.findById(idRol).orElse(null);
	}

	@Override
	public boolean existePorUsuarioAndRol(Usuario usuario, String rol) {
		return rolRepo.existsByUsuarioAndRol(usuario, rol);
	}

	@Override
	public Rol buscarPorUsuarioAndRol(Usuario usuario, String rol) {
		return rolRepo.findByUsuarioAndRol(usuario, rol);
	}

}
