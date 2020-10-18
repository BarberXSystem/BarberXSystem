package com.barber_x_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BarberXSystemApplication implements CommandLineRunner{
	
	@Autowired
	private BCryptPasswordEncoder passEnconder;

	public static void main(String[] args) {
		SpringApplication.run(BarberXSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String clavePrueba  = "admin";
		System.out.println(passEnconder.encode(clavePrueba));
		
	}

}
