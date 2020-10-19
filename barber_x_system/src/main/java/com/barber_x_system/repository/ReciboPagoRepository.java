package com.barber_x_system.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.barber_x_system.entity.ReciboPago;

@Repository
public interface ReciboPagoRepository extends CrudRepository<ReciboPago, Long> {

}
