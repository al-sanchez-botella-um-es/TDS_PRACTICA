package umu.tds.modelo;

import java.io.File;

public class FactoriaImportadorGastos {
	public static AdaptadorImportadorGastos getImportador(File archivo) {
		String nombre = archivo.getName().toLowerCase();
		if (nombre.endsWith(".csv")) return new AdaptadorCSV();
		if (nombre.endsWith(".json")) return new AdaptadorJSON();
		//if (nombre.endsWith(".txt")) return new AdaptadorTXT();
		//...
		throw new IllegalArgumentException("Formato no soportado: " + nombre);
	}
}