package com.barber_x_system.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.barber_x_system.entity.Administrador;
import com.barber_x_system.repository.AdminRepository;
import com.barber_x_system.service.IAdminServ;

@Service
public class AdminServImp implements IAdminServ{
	
	@Autowired
	private AdminRepository adminRepo;

	@Override
	public List<Administrador> listar() {
		return (List<Administrador>) adminRepo.findAll();
	}

	@Override
	public void guardar(Administrador admin) {
		adminRepo.save(admin);
	}

	@Override
	public void eliminar(Long idAdmin) {
		adminRepo.deleteById(idAdmin);
	}

	@Override
	public Administrador buscarPorId(Long idAdmin) {
		return adminRepo.findById(idAdmin).orElse(null);
	}

}
