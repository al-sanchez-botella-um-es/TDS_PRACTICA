package umu.tds.modelo;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
- id : Integer
- nombre : String
- fecha : LocalDateTime
- cantidad : Double
--
+ actualizar() -> operación del Controlador relacionada con esta clase
*/

public class Gasto {
	//@JsonProperty("NombreGasto")
	private String nombreGasto;
	@JsonProperty("Categoria")
	private Categoria categoria;
	@JsonProperty("Cantidad")
	private Double cantidad;
	@JsonProperty("Fecha")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate fecha;
	
	public Gasto() {}	//necesario para Jackson
	
	public Gasto(String nombre, Categoria categoria, Double cantidad, LocalDate fecha) {
		this.nombreGasto = nombre;
		this.categoria = categoria;
		this.cantidad = cantidad;
		this.fecha = fecha;
	}
	//esto se usa para la cuenta compartida
	public Gasto(String nombre, Double cantidad) {
		this.nombreGasto = nombre;
		this.cantidad = cantidad;
	}
	
	//A parte de necesitar los métodos get(), incluiremos los métodos set()
	// para los updates de los gastos
	public String getNombre() {
		return nombreGasto;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public Double getCantidad() {
		return cantidad;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setNombre(String nuevoNombre) {
		this.nombreGasto = nuevoNombre;
	}
	
	public void setFecha(LocalDate nuevaFecha) {
		this.fecha = nuevaFecha;
	}
	
	public void setCantidad(Double nuevaCantidad) {
		this.cantidad = nuevaCantidad;
	}
	
	public void setCategoria(Categoria nuevaCategoria) {
		this.categoria = nuevaCategoria;
	}
	
	@Override
	public String toString() {
	    return "Gasto: " + nombreGasto
	    		+ ", Categoria: " + categoria.getNombre()
	    		+ ", Cantidad: " + cantidad + "€"
	    		+ ", Fecha: " + fecha;
	}
}
