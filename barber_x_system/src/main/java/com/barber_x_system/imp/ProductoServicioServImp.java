package com.barber_x_system.imp;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
		productoServ.setDescripcion(productoServ.getDescripcion().toUpperCase());
		productoServ.setNombre(productoServ.getNombre().toUpperCase());
		
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

	@Override
	public boolean existePorNombre(String nombre) {
		return prodServRepo.existsByNombre(nombre);
	}

	@Override
	public List<ProductoServicio> buscarProductos() {
		return prodServRepo.findByCategoria("PRODUCTO");
	}

	@Override
	public List<ProductoServicio> buscarServicios() {
		return prodServRepo.findByCategoria("SERVICIO");
	}

	@Override
	public List<ProductoServicio> buscarProductoNombre(String nombre) {
		return prodServRepo.findByNombreContainingAndCategoria(nombre, "PRODUCTO");
	}

	@Override
	public List<ProductoServicio> buscarServicioNombre(String nombre) {
		return prodServRepo.findByNombreContainingAndCategoria(nombre, "SERVICIO");
	}

	@Override
	public boolean saveDataFromUploadFile(MultipartFile file) {
		boolean isFlag = false;
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
			isFlag = readDataFromExcel(file);
		}
		return isFlag;
	}
	
	private boolean readDataFromExcel(MultipartFile file) {
		Workbook workbook = getWorkBook(file);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		
		rows.next();
		
		while (rows.hasNext()) {
			
			try {
				Row row = rows.next();
				ProductoServicio prodServ = new ProductoServicio();
				
				if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
					prodServ.setNombre(row.getCell(0).getStringCellValue().toUpperCase());
				
					if (prodServRepo.existsByNombre(row.getCell(0).getStringCellValue())) {
						return false;
					}
				}
				
				if (row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING) {
					prodServ.setDescripcion(row.getCell(1).getStringCellValue().toUpperCase());
				}
				
				if (row.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC) {
					String price = NumberToTextConverter.toText(row.getCell(2).getNumericCellValue());
					Long precio = Long.parseLong(price);
					
					prodServ.setPrecioCompra(precio);
				}	else if (row.getCell(2).getCellType() == Cell.CELL_TYPE_STRING) {
					String price = NumberToTextConverter.toText(row.getCell(2).getNumericCellValue());
					Long precio = Long.parseLong(price);
					
					prodServ.setPrecioCompra(precio);
				}
				
				if (row.getCell(3).getCellType() == Cell.CELL_TYPE_NUMERIC) {
					String price = NumberToTextConverter.toText(row.getCell(3).getNumericCellValue());
					Long precio = Long.parseLong(price);
					
					prodServ.setPrecioVenta(precio);
				}	else if (row.getCell(3).getCellType() == Cell.CELL_TYPE_STRING) {
					String price = NumberToTextConverter.toText(row.getCell(3).getNumericCellValue());
					Long precio = Long.parseLong(price);
					
					prodServ.setPrecioVenta(precio);
				}
				
				if (row.getCell(4).getCellType() == Cell.CELL_TYPE_STRING) {
					prodServ.setCategoria(row.getCell(4).getStringCellValue().toUpperCase());
				}
				
				prodServRepo.save(prodServ);
			} catch (Exception e) {
				System.out.println("Oh no!, Algo ha ocurrido durante el ingreso de datos!");
			}
			
		}
		
		return true;
	}
	
	private Workbook getWorkBook(MultipartFile file) {
		Workbook workbook = null;
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		
		try {
			if (extension.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file.getInputStream());
			} else if (extension.equalsIgnoreCase("xls")){
				workbook = new HSSFWorkbook(file.getInputStream());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return workbook;
	}

}
