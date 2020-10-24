package com.barber_x_system.imp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
	public List<String> turnosDisponibles(LocalDate fecha, Estilista estilista) {
		List<Cita> citasDelDia = citaRepo.findByFechaAndEstilista(fecha, estilista);
		List<String> turnosDisponibles = new ArrayList<String>();
		
		Calendar fechaActual = Calendar.getInstance();
		
		int diaActual = fechaActual.get(Calendar.DAY_OF_MONTH);
		int horaActual = fechaActual.get(Calendar.HOUR_OF_DAY);
		int minutoActual = fechaActual.get(Calendar.MINUTE);
		
		int diaCita = fecha.getDayOfMonth();
		
		if ((diaActual == diaCita && horaActual == 8 && (minutoActual >= 0 && minutoActual <= 30))
				|| (diaActual == diaCita && horaActual < 8)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("9:00 AM - 9:30 AM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 9 && minutoActual == 0)
				|| (diaActual == diaCita && horaActual < 9)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("9:30 AM - 10:00 AM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 9 && (minutoActual >= 0 && minutoActual <= 30))
				|| (diaActual == diaCita && horaActual < 9)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("10:00 AM - 10:30 AM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 10 && minutoActual == 0)
				|| (diaActual == diaCita && horaActual < 10)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("10:30 AM - 11:00 AM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 10 && (minutoActual >= 0 && minutoActual <= 30))
				|| (diaActual == diaCita && horaActual < 10)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("11:00 AM - 11:30 AM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 11 && minutoActual == 0)
				|| (diaActual == diaCita && horaActual < 11)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("11:30 AM - 12:00 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 11 && (minutoActual >= 0 && minutoActual <= 30))
				|| (diaActual == diaCita && horaActual < 11)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("12:00 PM - 12:30 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 12 && minutoActual == 0)
				|| (diaActual == diaCita && horaActual < 12)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("1:00 PM - 1:30 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 13 && minutoActual == 0)
				|| (diaActual == diaCita && horaActual < 13)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("1:30 PM - 2:00 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 13 && (minutoActual >= 0 && minutoActual <= 30))
				|| (diaActual == diaCita && horaActual < 13)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("2:00 PM - 2:30 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 14 && minutoActual == 0)
				|| (diaActual == diaCita && horaActual < 14)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("2:30 PM - 3:00 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 14 && (minutoActual >= 0 && minutoActual <= 30))
				|| (diaActual == diaCita && horaActual < 14)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("3:00 PM - 3:30 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 15 && minutoActual == 0)
				|| (diaActual == diaCita && horaActual < 15)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("3:30 PM - 4:00 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 15 && (minutoActual >= 0 && minutoActual <= 30))
				|| (diaActual == diaCita && horaActual < 15)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("4:00 PM - 4:30 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 16 && minutoActual == 0)
				|| (diaActual == diaCita && horaActual < 16)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("4:30 PM - 5:00 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 16 && (minutoActual >= 0 && minutoActual <= 30))
				|| (diaActual == diaCita && horaActual < 16)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("5:00 PM - 5:30 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 17 && minutoActual == 0)
				|| (diaActual == diaCita && horaActual < 17)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("5:30 PM - 6:00 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 17 && (minutoActual >= 0 && minutoActual <= 30))
				|| (diaActual == diaCita && horaActual < 17)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("6:00 PM - 6:30 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 18 && minutoActual == 0)
				|| (diaActual == diaCita && horaActual < 18)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("6:30 PM - 7:00 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 18 && (minutoActual >= 0 && minutoActual <= 30))
				|| (diaActual == diaCita && horaActual < 18)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("7:00 PM - 7:30 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 19 && minutoActual == 0)
				|| (diaActual == diaCita && horaActual < 19)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("7:30 PM - 8:00 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 19 && (minutoActual >= 0 && minutoActual <= 30))
				|| (diaActual == diaCita && horaActual < 19)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("8:00 PM - 8:30 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 20 && minutoActual == 0)
				|| (diaActual == diaCita && horaActual < 20)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("8:30 PM - 9:00 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 20 && (minutoActual >= 0 && minutoActual <= 30))
				|| (diaActual == diaCita && horaActual < 20)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("9:00 PM - 9:30 PM");
			
		} 
		
		if ((diaActual == diaCita && horaActual == 21 && minutoActual == 0)
				|| (diaActual == diaCita && horaActual < 21)
				|| (diaCita > diaActual)) {
			turnosDisponibles.add("9:30 PM - 10:00 PM");
			
		}
		
		if (!citasDelDia.isEmpty()) {
			for (Cita cita : citasDelDia) {
				if (turnosDisponibles.contains(cita.getHora()) && (cita.getEstado().equals("PROGRAMADA") || cita.getEstado().equals("FINALIZADA"))) {
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
	public List<Cita> buscarPorFechaAndEstilista(LocalDate fecha, Estilista estilista) {
		return citaRepo.findByFechaAndEstilista(fecha, estilista);
	}

	@Override
	public List<Cita> buscarPorUsuario(Usuario usuario) {
		return citaRepo.findByUsuario(usuario);
	}

	@Override
	public boolean validCancelCita(Cita cita) {
		Calendar fechaActual = Calendar.getInstance();
		String hora = cita.getHora();
		int diaCita = cita.getFecha().getDayOfMonth();
		
		int diaActual = fechaActual.get(Calendar.DAY_OF_MONTH);
		int horaActual = fechaActual.get(Calendar.HOUR_OF_DAY);
		int minutoActual = fechaActual.get(Calendar.MINUTE);
		
		if ((hora.equals("9:00 AM - 9:30 AM") && diaActual < diaCita)
				|| hora.equals("9:00 AM - 9:30 AM") && diaActual == diaCita && (horaActual <= 8 && minutoActual <= 30 )) {
			return true;
		} else if ((hora.equals("9:30 AM - 10:00 AM") && diaActual < diaCita)
				|| (hora.equals("9:30 AM - 10:00 AM") && diaActual == diaCita && horaActual <= 9)) {
			return true;
		} else if ((hora.equals("10:00 AM - 10:30 AM") && diaActual < diaCita)
				|| hora.equals("10:00 AM - 10:30 AM") && diaActual == diaCita && (horaActual <= 9 && minutoActual <= 30 )) {
			return true;
		} else if ((hora.equals("10:30 AM - 11:00 AM") && diaActual < diaCita)
				|| (hora.equals("10:30 AM - 11:00 AM") && diaActual == diaCita && horaActual <= 10)) {
			return true;
		} else if ((hora.equals("11:00 AM - 11:30 AM") && diaActual < diaCita)
				|| hora.equals("11:00 AM - 11:30 AM") && diaActual == diaCita && (horaActual <= 10 && minutoActual <= 30 )) {
			return true;
		} else if ((hora.equals("11:30 AM - 12:00 PM") && diaActual < diaCita)
				|| (hora.equals("11:30 AM - 12:00 PM") && diaActual == diaCita && horaActual <= 11)) {
			return true;
		} else if ((hora.equals("12:00 PM - 12:30 PM") && diaActual < diaCita)
				|| hora.equals("12:00 PM - 12:30 PM") && diaActual == diaCita && (horaActual <= 11 && minutoActual <= 30 )) {
			return true;
		} else if ((hora.equals("1:00 PM - 1:30 PM") && diaActual < diaCita)
				|| hora.equals("1:00 PM - 1:30 PM") && diaActual == diaCita && (horaActual <= 12 && minutoActual <= 30 )) {
			return true;
		} else if ((hora.equals("1:30 AM - 2:00 PM") && diaActual < diaCita)
				|| (hora.equals("1:30 AM - 2:00 PM") && diaActual == diaCita && horaActual <= 13)) {
			return true;
		} else if ((hora.equals("2:00 PM - 2:30 PM") && diaActual < diaCita)
				|| hora.equals("2:00 PM - 2:30 PM") && diaActual == diaCita && (horaActual <= 13 && minutoActual <= 30 )) {
			return true;
		} else if ((hora.equals("2:30 AM - 3:00 PM") && diaActual < diaCita)
				|| (hora.equals("2:30 AM - 3:00 PM") && diaActual == diaCita && horaActual <= 14)) {
			return true;
		} else if ((hora.equals("3:00 PM - 3:30 PM") && diaActual < diaCita)
				|| hora.equals("3:00 PM - 3:30 PM") && diaActual == diaCita && (horaActual <= 14 && minutoActual <= 30 )) {
			return true;
		} else if ((hora.equals("3:30 AM - 4:00 PM") && diaActual < diaCita)
				|| (hora.equals("3:30 AM - 4:00 PM") && diaActual == diaCita && horaActual <= 15)) {
			return true;
		} else if ((hora.equals("4:00 PM - 4:30 PM") && diaActual < diaCita)
				|| hora.equals("4:00 PM - 4:30 PM") && diaActual == diaCita && (horaActual <= 15 && minutoActual <= 30 )) {
			return true;
		} else if ((hora.equals("4:30 AM - 5:00 PM") && diaActual < diaCita)
				|| (hora.equals("4:30 AM - 5:00 PM") && diaActual == diaCita && horaActual <= 16)) {
			return true;
		} else if ((hora.equals("5:00 PM - 5:30 PM") && diaActual < diaCita)
				|| hora.equals("5:00 PM - 5:30 PM") && diaActual == diaCita && (horaActual <= 16 && minutoActual <= 30 )) {
			return true;
		} else if ((hora.equals("5:30 AM - 6:00 PM") && diaActual < diaCita)
				|| (hora.equals("5:30 AM - 6:00 PM") && diaActual == diaCita && horaActual <= 17)) {
			return true;
		} else if ((hora.equals("6:00 PM - 6:30 PM") && diaActual < diaCita)
				|| hora.equals("6:00 PM - 6:30 PM") && diaActual == diaCita && (horaActual <= 17 && minutoActual <= 30 )) {
			return true;
		} else if ((hora.equals("6:30 AM - 7:00 PM") && diaActual < diaCita)
				|| (hora.equals("6:30 AM - 7:00 PM") && diaActual == diaCita && horaActual <= 18)) {
			return true;
		} else if ((hora.equals("7:00 PM - 7:30 PM") && diaActual < diaCita)
				|| hora.equals("7:00 PM - 7:30 PM") && diaActual == diaCita && (horaActual <= 18 && minutoActual <= 30 )) {
			return true;
		} else if ((hora.equals("7:30 AM - 8:00 PM") && diaActual < diaCita)
				|| (hora.equals("7:30 AM - 8:00 PM") && diaActual == diaCita && horaActual <= 19)) {
			return true;
		} else if ((hora.equals("8:00 PM - 8:30 PM") && diaActual < diaCita)
				|| hora.equals("8:00 PM - 8:30 PM") && diaActual == diaCita && (horaActual <= 19 && minutoActual <= 30 )) {
			return true;
		} else if ((hora.equals("8:30 AM - 9:00 PM") && diaActual < diaCita)
				|| (hora.equals("8:30 AM - 9:00 PM") && diaActual == diaCita && horaActual <= 20)) {
			return true;
		} else if ((hora.equals("9:00 PM - 9:30 PM") && diaActual < diaCita)
				|| hora.equals("9:00 PM - 9:30 PM") && diaActual == diaCita && (horaActual <= 20 && minutoActual <= 30 )) {
			return true;
		} else if ((hora.equals("9:30 AM - 10:00 PM") && diaActual < diaCita)
				|| (hora.equals("9:30 AM - 10:00 PM") && diaActual == diaCita && horaActual <= 21)) {
			return true;
		}
		return false;
	}

	
	@Override
	public boolean validOwnCita(Usuario usuario, Cita cita) {
		if (usuario.getIdUsuario().equals(cita.getUsuario().getIdUsuario())) {
			return true;
		}
		return false;
	}

}
