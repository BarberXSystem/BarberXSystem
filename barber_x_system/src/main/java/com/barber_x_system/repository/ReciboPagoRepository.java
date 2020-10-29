package com.barber_x_system.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.barber_x_system.entity.Estilista;
import com.barber_x_system.entity.ReciboPago;

@Repository
public interface ReciboPagoRepository extends CrudRepository<ReciboPago, Long> {
	
	public List<ReciboPago> findByFecha(Date fecha);
	public List<ReciboPago> findByFechaBetween(Date startDate, Date endDate);
	public List<ReciboPago> findByEstilista(Estilista estilista);
	public List<ReciboPago> findByEstilistaAndFecha(Estilista estilista, Date fecha);
	public List<ReciboPago> findByFechaBetweenAndEstilista(Date startDate, Date endDate, Estilista estilista);

}
