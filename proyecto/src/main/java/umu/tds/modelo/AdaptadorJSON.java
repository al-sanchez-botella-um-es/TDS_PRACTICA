package umu.tds.modelo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdaptadorJSON implements AdaptadorImportadorGastos {
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	@Override
	public List<Gasto> importar(File archivo) throws IOException {
		List<Gasto> gastos = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(archivo);
		for (JsonNode nodo : root) {
			String nombre = nodo.get("nombre").asText();
			String categoriaStr = nodo.get("Categoria").asText();
			Double cantidad = nodo.get("Cantidad").asDouble();
			String fechaStr = nodo.get("Fecha").asText();
			
			LocalDate fecha = LocalDate.parse(fechaStr, FORMATO_FECHA);
			Categoria categoria = new Categoria(categoriaStr);
			gastos.add(new Gasto(nombre, categoria, cantidad, fecha));
		}
		return gastos;
	}
}