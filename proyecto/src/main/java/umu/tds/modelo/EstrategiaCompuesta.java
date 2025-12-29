package umu.tds.modelo;

import java.util.Arrays;
import java.util.List;

public class EstrategiaCompuesta implements EstrategiaAlerta {
	private List<EstrategiaAlerta> estrategias;
	
	public EstrategiaCompuesta(EstrategiaAlerta... estrategias) {
		this.estrategias = Arrays.asList(estrategias);
	}
	
	@Override
	public boolean isAlertaActivada(List<Gasto> gastos, Alerta alerta) {
		return estrategias.stream()
				.allMatch(e -> e.isAlertaActivada(gastos, alerta));
	}
}