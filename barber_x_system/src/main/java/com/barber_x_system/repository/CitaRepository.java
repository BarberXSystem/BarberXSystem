package com.barber_x_system.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.barber_x_system.entity.Cita;
import com.barber_x_system.entity.Estilista;

@Repository
public interface CitaRepository extends CrudRepository<Cita, Long> {
	
	public List<Cita> findByFechaAndEstilista(Date fecha, Estilista estilista);

}
