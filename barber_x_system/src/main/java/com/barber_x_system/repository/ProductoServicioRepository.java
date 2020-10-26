package com.barber_x_system.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.barber_x_system.entity.ProductoServicio;

@Repository
public interface ProductoServicioRepository extends CrudRepository<ProductoServicio, Long> {
	
	public boolean existsByNombre(String nombre);
	public List<ProductoServicio> findByCategoria(String categoria);
	public List<ProductoServicio> findByNombreContainingAndCategoria(String nombre, String categoria);

}
