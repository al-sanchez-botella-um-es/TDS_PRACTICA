package umu.tds.modelo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class AdaptadorCSV implements AdaptadorImportadorGastos {
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("M/d/yyyy H:mm");
	
	@Override
	public List<Gasto> importar(File archivo) throws IOException {
		List<Gasto> gastos = new ArrayList<>();
		try (CSVReader reader = new CSVReader(new FileReader(archivo))) {	//dependencia de Maven
			// Saltar cabecera
			reader.readNext();
			String[] datos;
			while ((datos = reader.readNext()) != null) {
				if (datos.length < 8) continue;
				
				String fechaStr = datos[0].trim();
				String categoriaStr = datos[3].trim();
				String nombre = datos[4].trim();
				Double cantidad = Double.parseDouble(datos[6].trim());
				
				LocalDateTime fechaHora = LocalDateTime.parse(fechaStr, FORMATO_FECHA);
				LocalDate fecha = fechaHora.toLocalDate();
				
				Categoria categoria = new Categoria(categoriaStr);
				gastos.add(new Gasto(nombre, categoria, cantidad, fecha)); 
			}
		} catch (CsvValidationException e) {
			throw new IOException("Error al validar CSV: " + e.getMessage(), e);
		}
		return gastos;
	}
}