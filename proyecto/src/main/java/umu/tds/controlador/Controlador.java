package umu.tds.controlador;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import umu.tds.modelo.Alerta;
import umu.tds.modelo.Categoria;
import umu.tds.modelo.Gasto;
import umu.tds.modelo.Notificacion;
import umu.tds.repository.Repositorio;
import umu.tds.repository.impl.RepositorioAlertaJSON;
import umu.tds.repository.impl.RepositorioCategoriaJSON;
import umu.tds.repository.impl.RepositorioGastoJSON;
import umu.tds.repository.impl.RepositorioNotificacionJSON;

public class Controlador {
	private Repositorio<Gasto> repositorioGasto = RepositorioGastoJSON.getInstance();
	private Repositorio<Categoria> repositorioCategoria = RepositorioCategoriaJSON.getInstance();
	private Repositorio<Alerta> repositorioAlerta = RepositorioAlertaJSON.getInstance();
	private Repositorio<Notificacion> repositorioNotificacion = RepositorioNotificacionJSON.getInstance();
	
	public Controlador() {
		this.repositorioGasto = RepositorioGastoJSON.getInstance();
		this.repositorioCategoria = RepositorioCategoriaJSON.getInstance();
		this.repositorioAlerta = RepositorioAlertaJSON.getInstance();
		this.repositorioNotificacion = RepositorioNotificacionJSON.getInstance();
	}
	
	public ObservableList<Gasto> getGastos() {
	    return repositorioGasto.findAll();
	}
	
	public List<Gasto> getGastosCalendario() {
	    return repositorioGasto.findAll().stream().toList();
	}
	//Calendario por día
	public List<Gasto> getGastosPorFecha(LocalDate fecha) {
	    return getGastos().stream()
	            .filter(g -> g.getFecha().equals(fecha))
	            .toList();
	}
	//Calendario por mes
	public List<Gasto> getGastosPorMes(Month mes) {
	    return getGastos().stream()
	            .filter(g -> g.getFecha().getMonth() == mes)
	            .toList();
	}
	//Servirá para la representación gráfica
	public Map<Categoria, Double> getGastoTotalPorCategoria() {
	    return getGastos().stream()
	            .collect(Collectors.groupingBy(
	                    Gasto::getCategoria,
	                    Collectors.summingDouble(Gasto::getCantidad)
	            ));
	}
	
	public ObservableList<Categoria> getCategorias() {
	    return repositorioCategoria.findAll();
	}
	
	public List<Alerta> getAlertas() {
		return repositorioAlerta.findAll();
	}
	
	public ObservableList<Notificacion> getNotificaciones() {
		return repositorioNotificacion.findAll();
	}
	
	///Métodos referentes a las Historias de Usuario
	public Gasto addGasto(String nombre, String categoriaStr, double cantidad, LocalDate fecha) {
		Categoria categoria = repositorioCategoria.findAll().stream()
		        .filter(c -> c.getNombre().equalsIgnoreCase(categoriaStr))
		        .findFirst()
		        .orElse(null);
		if (categoria == null) {
	        return null; // o lanzar IllegalArgumentException
	    }
		Gasto gasto = new Gasto(nombre, categoria, cantidad, fecha);
		repositorioGasto.save(gasto);
		return gasto;
	}
	
	public void removeGasto(Gasto gasto) {
		repositorioGasto.delete(gasto);
	}
	
	public void modifyGasto(Gasto gasto) {
		repositorioGasto.modify(gasto);
	}
	
	public Categoria addCategoria(String categoriaStr) {
		Categoria existente = repositorioCategoria.findAll().stream()
		        .filter(c -> c.getNombre().equalsIgnoreCase(categoriaStr))
		        .findFirst()
		        .orElse(null);
		if (existente != null) {
			return existente;
		}
		Categoria nueva = new Categoria(categoriaStr);
        repositorioCategoria.save(nueva);
        return nueva;
	}
	
	public void addNotificacion(String mensaje) {
		repositorioNotificacion.save(new Notificacion(mensaje));
	}

	public Alerta configurarAlerta(String frecuenciaStr, String categoriaStr, double limite) {
		Alerta.Frecuencia frecuencia = Alerta.Frecuencia.valueOf(frecuenciaStr.toUpperCase());
		Categoria categoria = repositorioCategoria.findAll().stream()
				.filter(c -> c.getNombre().equalsIgnoreCase(categoriaStr))
				.findFirst()
				.orElse(null);	//debe estar creada al añadir un gasto!!
		Alerta alerta = new Alerta(frecuencia, categoria, limite);
		repositorioAlerta.save(alerta);
		return alerta;
	}
	
	public void removeAlerta(Alerta alerta) {
		repositorioAlerta.delete(alerta);
	}

	public void visualizarAlerta() {
		 
	}

	//comprobar que alertas han saltado / caducado -> stream
	public void comprobarAlertas() {
	    for (Alerta alerta : getAlertas()) {
	        if (alerta.comprobarAlerta(getGastos())) {
	            addNotificacion("Alerta activada: " + alerta.toString());
	        }
	    }
	}

	public void importarGastos() {
		
	}
	
	//se buscará con las fechas inclusive
	public List<Gasto> filtrarFecha(LocalDate fechainicio, LocalDate fechafin) {
		return getGastos().stream()
				.filter(f -> !f.getFecha().isBefore(fechainicio) && !f.getFecha().isAfter(fechafin))
				.collect(Collectors.toList());
	}
	
	///El método cogerá los meses seleccionados en el ComboBox y buscará gastos pertenecientes
	/// a esos meses
	public List<Gasto> filtrarFechaPorMeses(Month... mes) {
	    Set<Month> listaMeses = Set.of(mes); 	//convertir para búsqueda rápida
		return getGastos().stream()
				.filter(g -> listaMeses.contains(g.getFecha().getMonth()))
				.toList();
	}

	public List<Categoria> filtrarCategoria(Categoria categoria) {
		return getCategorias().stream()
				.filter(c -> c.getNombre().equals(categoria.getNombre()))
				.toList();
	}
	
	/*public List<Gasto> filtrarGastos(
	        Set<Month> meses,               // meses seleccionados (puede ser vacío o null)
	        LocalDate fechaInicio,          // fecha inicial (puede ser null)
	        LocalDate fechaFin,             // fecha final (puede ser null)
	        Set<Categoria> categorias       // categorías seleccionadas (puede ser vacío o null)
	) {
	    return getGastos().stream()
	            .filter(g -> {
	                if (meses != null && !meses.isEmpty()) {
	                    if (!meses.contains(g.getFecha().getMonth())) return false;
	                }
	                if (fechaInicio != null && g.getFecha().isBefore(fechaInicio)) return false;
	                if (fechaFin != null && g.getFecha().isAfter(fechaFin)) return false;
	                if (categorias != null && !categorias.isEmpty()) {
	                    if (!categorias.contains(g.getCategoria())) return false;
	                }
	                return true;
	            })
	            .collect(Collectors.toList());
	}*/
	
	//public List<Gasto> filtrarCombinacion() {}
}