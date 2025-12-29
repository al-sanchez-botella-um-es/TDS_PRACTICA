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
	
	public Notificacion() {};
	
	public Notificacion(String mensaje, LocalDate fecha) {
		this.mensaje = mensaje;
		this.fecha = fecha;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	@Override public String toString() {
		return fecha + " - " + mensaje;
	}
}