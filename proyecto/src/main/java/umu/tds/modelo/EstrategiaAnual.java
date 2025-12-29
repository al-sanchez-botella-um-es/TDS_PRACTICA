package umu.tds.modelo;

import java.time.LocalDate;
import java.util.List;

public class EstrategiaAnual implements EstrategiaAlerta {
	@Override
	public boolean isAlertaActivada(List<Gasto> gastos, Alerta alerta) {
		LocalDate hoy = LocalDate.now();
		double total = gastos.stream()
				.filter(g -> g.getFecha().getYear() == hoy.getYear())
				.filter(g -> alerta.getCategoria() == null || g.getCategoria().equals(alerta.getCategoria()))
				.mapToDouble(Gasto::getCantidad)
				.sum();
		return total > alerta.getLimite();
	}
}