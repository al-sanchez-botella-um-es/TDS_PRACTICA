package umu.tds.repository.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import umu.tds.modelo.Notificacion;
import umu.tds.repository.Repositorio;

//Necesaria para el historial de notificaciones


public class RepositorioNotificacionJSON implements Repositorio<Notificacion> {
	private static RepositorioNotificacionJSON instancia;
	private final File fichero = new File("notificaciones.json");
	private final ObjectMapper mapper;
	private final ObservableList<Notificacion> notificaciones = FXCollections.observableArrayList();
	
	private RepositorioNotificacionJSON() {
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule()); // soporte para LocalDate
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		cargar();
	}
	
	public static RepositorioNotificacionJSON getInstance() {
		if (instancia == null) {
			instancia = new RepositorioNotificacionJSON();
		}
		return instancia;
	}
	
	@Override
	public void save(Notificacion n) {
		notificaciones.add(n);
		guardar();
	}
	
	@Override
	public void delete(Notificacion n) {
		// no permitido
	}
	
	@Override
	public ObservableList<Notificacion> findAll() {
		return notificaciones;
	}
	
	@Override
	public void modify(Notificacion n) {
		// No permitido
	}
	
	private void guardar() {
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, notificaciones);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void cargar() {
		if (fichero.exists()) {
			try {
				List<Notificacion> lista = mapper.readValue(fichero, new TypeReference<List<Notificacion>>() {});
				notificaciones.setAll(lista);
			} catch (IOException e) {
				notificaciones.clear();
				guardar();
			}
		}
	}
}