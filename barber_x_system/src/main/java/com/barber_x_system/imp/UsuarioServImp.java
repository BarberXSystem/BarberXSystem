package com.barber_x_system.imp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.barber_x_system.entity.Usuario;
import com.barber_x_system.repository.UsuarioRepository;
import com.barber_x_system.service.IUsuarioServ;

@Service
public class UsuarioServImp implements IUsuarioServ{
	
	@Autowired
	public UsuarioRepository usuarioRepo;

	@Override
	public List<Usuario> listar() {
		return (List<Usuario>) usuarioRepo.findAll();
	}

	@Override
	public void guardar(Usuario usuario) {
		usuario.setNombres(usuario.getNombres().toUpperCase());
		usuario.setApellidos(usuario.getApellidos().toUpperCase());
		usuario.setDireccion(usuario.getDireccion().toUpperCase());
		usuario.setEmail(usuario.getEmail().toUpperCase());
		usuario.setUsername(usuario.getNumeroDoc());
		
		usuarioRepo.save(usuario);
	}

	@Override
	public void eliminar(Long idUsuario) {
		usuarioRepo.deleteById(idUsuario);
	}

	@Override
	public Usuario buscarPorId(Long idUsuario) {
		return usuarioRepo.findById(idUsuario).orElse(null);
	}

	@Override
	public boolean existePorNumeroDoc(String username) {
		return usuarioRepo.existsByNumeroDoc(username);
	}

	@Override
	public boolean existePorEmail(String email) {
		return usuarioRepo.existsByEmail(email);
	}

	@Override
	public boolean passMatch(Usuario usuario) {
		if (usuario.getPassword().equals(usuario.getConfirmPassword())) {
			return true;
		}
		return false;
	}

	@Override
	public Usuario buscarPorNumeroDoc(String numeroDoc) {
		return usuarioRepo.findByNumeroDoc(numeroDoc);
	}

}
