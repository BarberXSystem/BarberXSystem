package com.barber_x_system.imp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.barber_x_system.entity.ProductoServicio;
import com.barber_x_system.repository.ProductoServicioRepository;
import com.barber_x_system.service.IProductoServicioServ;

@Service
public class ProductoServicioServImp implements IProductoServicioServ{
	
	@Autowired
	private ProductoServicioRepository prodServRepo;

	@Override
	public List<ProductoServicio> listar() {
		return (List<ProductoServicio>) prodServRepo.findAll();
	}

	@Override
	public void guardar(ProductoServicio productoServ) {
		prodServRepo.save(productoServ);
	}

	@Override
	public void eliminar(Long idProductoServ) {
		prodServRepo.deleteById(idProductoServ);
	}

	@Override
	public ProductoServicio buscarPorId(Long idProductoServ) {
		return prodServRepo.findById(idProductoServ).orElse(null);
	}

}
