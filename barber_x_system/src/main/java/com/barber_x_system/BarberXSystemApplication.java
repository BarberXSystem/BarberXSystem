package com.barber_x_system;

import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;
import com.barber_x_system.entity.Cita;
import com.barber_x_system.service.ICitaServ;

@SpringBootApplication
public class BarberXSystemApplication {
	
	@Autowired
	private ICitaServ citaService;

	public static void main(String[] args) {
		SpringApplication.run(BarberXSystemApplication.class, args);
	}
	
	@Transactional
	@SuppressWarnings("deprecation")
	@Scheduled(initialDelay = 1000L, fixedDelayString = "PT15M")
	public void actualizarCitas() throws InterruptedException {
		List<Cita> citas = citaService.listar();
		Date fechaActual = new Date();
		
		for (Cita cita : citas) {
			
			if (((cita.getFecha().getYear() < fechaActual.getYear())
					|| (cita.getFecha().getYear() == fechaActual.getYear() && cita.getFecha().getMonth() < fechaActual.getMonth())
					|| (cita.getFecha().getYear() == fechaActual.getYear() && cita.getFecha().getMonth() == fechaActual.getMonth() && cita.getFecha().getDate() < fechaActual.getDate())
					|| (cita.getFecha().getYear() == fechaActual.getYear() && cita.getFecha().getMonth() == fechaActual.getMonth() && cita.getFecha().getDate() == fechaActual.getDate() && horaVencida(cita.getHora(), fechaActual)))
					&& (cita.getEstado().equals("PROGRAMADA"))) {
				cita.setEstado("CANCELADA");
			}
			
			citaService.guardar(cita);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public boolean horaVencida(String hora, Date fechaActual) {
		if ((hora.equals("9:00 AM - 9:30 AM") && fechaActual.getHours() == 9 && fechaActual.getMinutes() >= 30)
				|| (hora.equals("9:30 AM - 10:00 AM") && fechaActual.getHours() == 10)
				|| (hora.equals("10:00 AM - 10:30 AM") && fechaActual.getHours() == 10 && fechaActual.getMinutes() >= 30)
				|| (hora.equals("10:30 AM - 11:00 AM") && fechaActual.getHours() == 11)
				|| (hora.equals("11:00 AM - 10:30 AM") && fechaActual.getHours() == 11 && fechaActual.getMinutes() >= 30)
				|| (hora.equals("11:30 AM - 12:00 AM") && fechaActual.getHours() == 12)
				|| (hora.equals("12:00 AM - 12:30 AM") && fechaActual.getHours() == 12 && fechaActual.getMinutes() >= 30)
				|| (hora.equals("1:00 PM - 1:30 PM") && fechaActual.getHours() == 13 && fechaActual.getMinutes() >= 30)
				|| (hora.equals("1:30 PM - 2:00 PM") && fechaActual.getHours() == 14)
				|| (hora.equals("2:00 PM - 2:30 PM") && fechaActual.getHours() == 14 && fechaActual.getMinutes() >= 30)
				|| (hora.equals("2:30 PM - 3:00 PM") && fechaActual.getHours() == 15)
				|| (hora.equals("3:00 PM - 3:30 PM") && fechaActual.getHours() == 15 && fechaActual.getMinutes() >= 30)
				|| (hora.equals("3:30 PM - 4:00 PM") && fechaActual.getHours() == 16)
				|| (hora.equals("4:00 PM - 4:30 PM") && fechaActual.getHours() == 16 && fechaActual.getMinutes() >= 30)
				|| (hora.equals("4:30 PM - 5:00 PM") && fechaActual.getHours() == 17)
				|| (hora.equals("5:00 PM - 5:30 PM") && fechaActual.getHours() == 17 && fechaActual.getMinutes() >= 30)
				|| (hora.equals("5:30 PM - 6:00 PM") && fechaActual.getHours() == 18)
				|| (hora.equals("6:00 PM - 6:30 PM") && fechaActual.getHours() == 18 && fechaActual.getMinutes() >= 30)
				|| (hora.equals("6:30 PM - 7:00 PM") && fechaActual.getHours() == 19)
				|| (hora.equals("7:00 PM - 7:30 PM") && fechaActual.getHours() == 19 && fechaActual.getMinutes() >= 30)
				|| (hora.equals("7:30 PM - 8:00 PM") && fechaActual.getHours() == 20)
				|| (hora.equals("8:00 PM - 8:30 PM") && fechaActual.getHours() == 20 && fechaActual.getMinutes() >= 30)
				|| (hora.equals("8:30 PM - 9:00 PM") && fechaActual.getHours() == 21)
				|| (hora.equals("9:00 PM - 9:30 PM") && fechaActual.getHours() == 21 && fechaActual.getMinutes() >= 30)
				|| (hora.equals("9:30 PM - 10:00 PM") && fechaActual.getHours() == 22)
				) {
			return true;
		}
		
		return false;
	}

}
