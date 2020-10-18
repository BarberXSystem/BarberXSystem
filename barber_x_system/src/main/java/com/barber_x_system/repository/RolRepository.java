package com.barber_x_system.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.barber_x_system.entity.Rol;
import com.barber_x_system.entity.Usuario;

@Repository
public interface RolRepository extends CrudRepository<Rol, Long> {

	public boolean existsByUsuarioAndRol(Usuario usuario, String rol);
	public Rol findByUsuarioAndRol(Usuario usuario, String rol);
	
	
}
