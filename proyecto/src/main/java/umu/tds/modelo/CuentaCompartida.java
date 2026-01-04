package umu.tds.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CuentaCompartida {
	@JsonProperty("Nombre")
	private String nombre;
	@JsonProperty("Participantes")
    private List<Participante> participantes;
    private boolean participantesFijados = false; // necesario para que la lista de participantes no se modifique
    
    public CuentaCompartida() {
        this.participantes = new ArrayList<>();
    }

    public CuentaCompartida(String nombre) {
    	this.nombre = nombre;
        this.participantes = new ArrayList<>();
    }
    
    public String getNombre() {
    	return nombre;
    }
    
    public void setNombre(String nombre) {
    	this.nombre = nombre;
    }

    public List<Participante> getParticipantes() {
        return Collections.unmodifiableList(participantes);
    }

    public void addParticipante(String nombre, double saldoInicial) {
        if (participantesFijados)
            throw new IllegalStateException("La lista de participantes ya está fijada y no se puede modificar.");

        boolean existe = participantes.stream()
                .anyMatch(p -> p.getNombre().equals(nombre));
        if (!existe) {
            participantes.add(new Participante(nombre, saldoInicial));
        }
    }

    public void removeParticipante(String nombre) {
        if (participantesFijados)
            throw new IllegalStateException("La lista de participantes ya está fijada y no se puede modificar.");

        participantes.removeIf(p -> p.getNombre().equals(nombre));
    }

    public void fijarParticipantes() {
        this.participantesFijados = true;
        // Si no hay porcentajes definidos, asignar reparto equitativo
        boolean todosCero = participantes.stream()
                .allMatch(p -> p.getPorcentaje() == 0.0);

        if (todosCero) {
            asignarPorcentajePorDefecto();
        }
    }

    public boolean isParticipantesFijados() {
        return participantesFijados;
    }

    public Participante getParticipante(String nombre) {
        return participantes.stream()
                .filter(p -> p.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    public void asignarPorcentajePorDefecto() {
        if (participantes.isEmpty()) return;

        double cuota = 1.0 / participantes.size();
        for (Participante p : participantes) {
            p.setPorcentaje(cuota);
        }
    }

    public void asignarPorcentajesPersonalizados(List<Double> porcentajes) {
        if (participantes.size() != porcentajes.size())
            throw new IllegalArgumentException("Número de porcentajes distinto al número de participantes.");

        double suma = porcentajes.stream().mapToDouble(Double::doubleValue).sum();
        if (Math.abs(suma - 1.0) > 1e-6)
            throw new IllegalArgumentException("La suma de porcentajes debe ser 1.0 (100%).");

        for (int i = 0; i < participantes.size(); i++) {
            participantes.get(i).setPorcentaje(porcentajes.get(i));
        }
    }

    public double getGastoTotalDelGrupo() {
        return participantes.stream()
                .mapToDouble(Participante::getSaldo)
                .sum();
    }

    public void registrarGasto(String nombrePagador, double importe) {
        Participante pagador = getParticipante(nombrePagador);
        if (pagador == null)
            throw new IllegalArgumentException("No existe el participante: " + nombrePagador);
        // Sumar lo pagado
        pagador.setSaldo(pagador.getSaldo() + importe);
        // Recalcular balances
        actualizarBalances();
    }

    public void actualizarBalances() {
        if (participantes.isEmpty()) return;
        double total = getGastoTotalDelGrupo();
        boolean hayAlgunoConPorcentaje = participantes.stream()
                .anyMatch(p -> p.getPorcentaje() > 0.0);

        if (!hayAlgunoConPorcentaje) {
            asignarPorcentajePorDefecto();
        }
        for (Participante p : participantes) {
            double cuota = total * p.getPorcentaje();
            double pagado = p.getSaldo();
            double balance = pagado - cuota;

            balance = Math.round(balance * 100.0) / 100.0;
            p.setBalanceCalculado(balance);
        }
    }
}