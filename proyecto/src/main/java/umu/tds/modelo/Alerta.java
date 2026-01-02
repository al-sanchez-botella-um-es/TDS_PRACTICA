package umu.tds.modelo;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
- id : Integer
- categoria : Categoria
- fecha : LocalDateTime
- frecuencia : enum
- cantidad : double
*/

///El patrón Estrategia se aplica en el sistema de alertas para
/// permitir múltiples formas de calcular si un límite de gasto ha sido superado.
///Cada tipo de alerta (semanal, mensual, por categoría…) implementa una estrategia distinta,
/// mientras que la clase Alerta actúa como contexto delegando la lógica en la estrategia seleccionada.
///Esto permite añadir nuevos tipos de alertas sin modificar el código existente, cumpliendo el principio Open/Closed.

public class Alerta {
	//Atributos
	public enum Frecuencia {
        SEMANAL, MENSUAL, ANUAL
	};
	@JsonProperty("Frecuencia")
	private Frecuencia frecuencia;
	@JsonProperty("Categoria")
	private Categoria categoria;
	@JsonProperty("Fecha")
	private LocalDateTime fecha;
	@JsonProperty("CantidadLimite")
	private double cantidad;
	
	@JsonIgnore
	private EstrategiaAlerta estrategia;
	
	//Constructores
	public Alerta() {};
	
	public Alerta(Frecuencia frecuencia, Categoria categoria, double cantidad) {
		this.frecuencia = frecuencia;
		this.categoria = categoria;
		this.cantidad = cantidad;
		this.estrategia = seleccionarEstrategia(frecuencia);
	}
	
	public Alerta(Frecuencia frecuencia, Categoria categoria, LocalDateTime fecha, double cantidad) {
		this(frecuencia, categoria, cantidad);
		this.fecha = fecha;		//¿guardamos la fecha en la que saltó? ¿O mejor en Notificación?
	}
	
	private EstrategiaAlerta seleccionarEstrategia(Frecuencia f) {
		switch (f) {
			case SEMANAL:
				return new EstrategiaSemanal();
			case MENSUAL:
				return new EstrategiaMensual();
			case ANUAL:
				return new EstrategiaAnual();
			default:
				return null;
		}
	}
	
	public boolean comprobarAlerta(List<Gasto> gastos) {
		return estrategia != null && estrategia.isAlertaActivada(gastos, this);
	}
	
	//Métodos de consulta
	public Frecuencia getFrecuencia() {
		return frecuencia;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public double getLimite() {
		return cantidad;
	}
	
	public void setLimite(double limite) {
	    this.cantidad = limite;
	}
	
	public boolean coincideCon(Gasto gasto) {	//La alerta pertenece al gasto
	    return gasto.getCategoria().equals(this.categoria);
	}

	
	@Override
    public String toString() {
        return "Alerta → Frecuencia: " + frecuencia +
               ", Categoría: " + categoria.getNombre() +
               ", Límite: " + cantidad + " €";
	}
}