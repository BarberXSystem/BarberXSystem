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

}
