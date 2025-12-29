package umu.tds.modelo;

import java.util.List;

public interface EstrategiaAlerta {
	boolean isAlertaActivada(List<Gasto> gastos, Alerta alerta);
}