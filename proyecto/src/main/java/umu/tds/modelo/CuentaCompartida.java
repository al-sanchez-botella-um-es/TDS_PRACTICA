package umu.tds.modelo;

import java.util.ArrayList;
import java.util.List;

public class CuentaCompartida {

    private List<Participante> participantes;

    public CuentaCompartida() {
        this.participantes = new ArrayList<>();
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public void addParticipante(String nombre, double saldoInicial) {
        boolean existe = participantes.stream().anyMatch(p -> p.getNombre().equals(nombre));
        if (!existe) {
            participantes.add(new Participante(nombre, saldoInicial));
        }
    }

    public void removeParticipante(String nombre) {
        participantes.removeIf(p -> p.getNombre().equals(nombre));
    }

    public Participante getParticipante(String nombre) {
        return participantes.stream()
                .filter(p -> p.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }
    
    public void limpiarParticipantes() {
        this.participantes.clear();
    }

    public void asignarPorcentajePorDefecto() {
        if (participantes.isEmpty()) return;
        double cuota = 1.0 / participantes.size();
        for (Participante p : participantes) {
            p.setPorcentaje(cuota);
        }
    }
    
    //Calcular el total gastado por el grupo
    public double getGastoTotalDelGrupo() {
        return participantes.stream()
                            .mapToDouble(Participante::getSaldo) // Sumamos los saldos de todos
                            .sum();
    }

    public void actualizarBalances() {
        if (participantes.isEmpty()) return;

        double gastoTotalGrupo = getGastoTotalDelGrupo();
        
        if (participantes.get(0).getPorcentaje() == 0 && !participantes.isEmpty()) {
            asignarPorcentajePorDefecto();
        }

        for (Participante p : participantes) {
            double cuota = gastoTotalGrupo * p.getPorcentaje(); // Lo que LE TOCA pagar
            double pagado = p.getSaldo();                       // Lo que HA pagado
            double balance = pagado - cuota;
            // Redondeo a 2 decimales
            balance = Math.round(balance * 100.0) / 100.0;
            
            p.setBalanceCalculado(balance);
        }
    }
}