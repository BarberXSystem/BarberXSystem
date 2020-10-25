package com.barber_x_system.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.barber_x_system.entity.DetallePago;
import com.barber_x_system.entity.ReciboPago;

@Repository
public interface DetallePagoRepository extends CrudRepository<DetallePago, Long> {

	public List<DetallePago> findByRecibo(ReciboPago recibo);
	
}
