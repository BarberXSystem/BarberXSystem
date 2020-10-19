package com.barber_x_system;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private BCryptPasswordEncoder passEncoder;
	
	String[] resources = new String[] {"/", "/login", "/registro/**", "/include/**","/css/**","/icons/**", "/fonts/**","/img/**","/js/**","/layer/**"};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(resources).permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin().defaultSuccessUrl("/dashboard/", true).loginPage("/login")
		.permitAll()
		.and()
		.logout().permitAll();
	}

	@Autowired
	public void configurerSecurityGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passEncoder)
		.usersByUsernameQuery("SELECT username, password, enabled FROM usuarios WHERE username=?")
		.authoritiesByUsernameQuery("SELECT u.username, r.rol FROM roles r INNER JOIN usuarios u ON r.id_usuario = u.id_usuario WHERE username=?");
	}

}
