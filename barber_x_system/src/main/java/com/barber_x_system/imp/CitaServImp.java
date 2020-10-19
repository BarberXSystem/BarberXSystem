package com.barber_x_system.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.barber_x_system.entity.Cita;
import com.barber_x_system.entity.Estilista;
import com.barber_x_system.entity.Usuario;
import com.barber_x_system.repository.CitaRepository;
import com.barber_x_system.service.ICitaServ;

@Service
public class CitaServImp implements ICitaServ{
	
	@Autowired
	private CitaRepository citaRepo;

	@Override
	public List<Cita> listar() {
		return (List<Cita>) citaRepo.findAll();
	}

	@Override
	public void guardar(Cita cita) {
		citaRepo.save(cita);
	}

	@Override
	public void eliminar(Long idCita) {
		citaRepo.deleteById(idCita);
	}

	@Override
	public Cita buscarPorId(Long idCita) {
		return citaRepo.findById(idCita).orElse(null);
	}

	@Override
	public List<String> turnosDisponibles(Date fecha, Estilista estilista) {
		List<Cita> citasDelDia = citaRepo.findByFechaAndEstilista(fecha, estilista);
		List<String> turnosDisponibles = new ArrayList<String>();
		
		turnosDisponibles.add("9:00 AM / 9:30 AM");
		turnosDisponibles.add("9:30 AM / 10:00 AM");
		turnosDisponibles.add("10:00 AM / 10:30 AM");
		turnosDisponibles.add("10:30 AM / 11:00 AM");
		turnosDisponibles.add("11:00 AM / 11:30 AM");
		turnosDisponibles.add("11:30 AM / 12:00 PM");
		turnosDisponibles.add("12:00 PM / 12:30 PM");
		turnosDisponibles.add("1:00 PM / 1:30 PM");
		turnosDisponibles.add("1:30 PM / 2:00 PM");
		turnosDisponibles.add("2:00 PM / 2:30 PM");
		turnosDisponibles.add("2:30 PM / 3:00 PM");
		turnosDisponibles.add("3:00 PM / 3:30 PM");
		turnosDisponibles.add("3:30 PM / 4:00 PM");
		turnosDisponibles.add("4:00 PM / 4:30 PM");
		turnosDisponibles.add("4:30 PM / 5:00 PM");
		turnosDisponibles.add("5:00 PM / 5:30 PM");
		turnosDisponibles.add("5:30 PM / 6:00 PM");
		turnosDisponibles.add("6:00 PM / 6:30 PM");
		turnosDisponibles.add("6:30 PM / 7:00 PM");
		turnosDisponibles.add("7:00 PM / 7:30 PM");
		turnosDisponibles.add("7:30 PM / 8:00 PM");
		turnosDisponibles.add("8:00 PM / 8:30 PM");
		turnosDisponibles.add("8:30 PM / 9:00 PM");
		turnosDisponibles.add("9:00 PM / 9:30 PM");
		turnosDisponibles.add("9:30 PM / 10:00 PM");
		
		if (!citasDelDia.isEmpty()) {
			for (Cita cita : citasDelDia) {
				if (turnosDisponibles.contains(cita.getHora()) || cita.getEstado().equals("FINALIZADA")) {
					turnosDisponibles.remove(cita.getHora());
				}
			}
		}
		
		if (turnosDisponibles.isEmpty()) {
			return null;
		}
		return turnosDisponibles;
	}

	@Override
	public List<Cita> buscarPorFechaAndEstilista(Date fecha, Estilista estilista) {
		return citaRepo.findByFechaAndEstilista(fecha, estilista);
	}

	@Override
	public List<Cita> buscarPorUsuario(Usuario usuario) {
		return citaRepo.findByUsuario(usuario);
	}

}
