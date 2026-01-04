package umu.tds.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Participante {
	@JsonProperty("Nombre")
    private String nombre;
	@JsonProperty("Saldo")
    private double saldo;     // Lo que ha pagado
	@JsonProperty("Porcentaje")
    private double porcentaje; // Su cuota (0.0 a 1.0)
    private double balanceCalculado;
    
    public Participante() {};
    
    public Participante(String nombre, double saldo) {
        this.nombre = nombre;
        this.saldo = saldo;
        this.porcentaje = 0.0; // Se asignará después
    }

    public String getNombre() { 
    	return nombre; 
    }
    
    public double getSaldo() { 
    	return saldo; 
    }
    
    public double getPorcentaje() { 
    	return porcentaje; 
    }
    
    public String getPorcentajeVisual() { 
        return String.format("%.1f%%", porcentaje * 100); 
    }

    public String getBalanceTexto() {
        return String.format("%+.2f", balanceCalculado);
    }

    public void setSaldo(double saldo) { 
    	this.saldo = saldo; 
    }
    
    public void setPorcentaje(double porcentaje) { 
    	this.porcentaje = porcentaje; 
    }
    
    public void setBalanceCalculado(double balanceCalculado) {
		this.balanceCalculado = balanceCalculado;
	}
    
}
