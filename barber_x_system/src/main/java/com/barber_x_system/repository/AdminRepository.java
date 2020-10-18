package com.barber_x_system.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.barber_x_system.entity.Administrador;
import com.barber_x_system.entity.Usuario;

@Repository
public interface AdminRepository extends CrudRepository<Administrador, Long> {

	public Administrador findByUsuario(Usuario usuario);
	
}
