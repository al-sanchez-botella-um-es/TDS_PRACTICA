package umu.tds.modelo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
- mensaje : String
- fecha: LocalDate
--
+ visualizarAlerta()
*/

public class Notificacion {
	@JsonProperty("Mensaje")
	private String mensaje;
	@JsonProperty("Fecha")
	private LocalDate fecha;
	
	public Notificacion(String mensaje) {
		this.mensaje = mensaje;
		this.fecha = LocalDate.now();
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
}