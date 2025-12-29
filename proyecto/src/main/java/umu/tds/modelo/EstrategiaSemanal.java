package umu.tds.modelo;

import java.time.LocalDate;
import java.util.List;

public class EstrategiaSemanal implements EstrategiaAlerta {
	@Override
	public boolean isAlertaActivada(List<Gasto> gastos, Alerta alerta) {
		LocalDate hoy = LocalDate.now();
		LocalDate inicioSemana = hoy.minusDays(hoy.getDayOfWeek().getValue() - 1);
		double total = gastos.stream()
				.filter(g -> g.getFecha().isAfter(inicioSemana.minusDays(1)))
				.filter(g -> alerta.getCategoria() == null || g.getCategoria().equals(alerta.getCategoria()))
				.mapToDouble(Gasto::getCantidad)
				.sum();
		return total > alerta.getLimite();
	}
}