package umu.tds.repository.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import umu.tds.modelo.Alerta;
import umu.tds.repository.Repositorio;

public class RepositorioAlertaJSON implements Repositorio<Alerta> {
	private static RepositorioAlertaJSON instancia;
	private final File fichero = new File("alertas.json");
    private final ObjectMapper mapper;
    private final ObservableList<Alerta> alertas = FXCollections.observableArrayList();
    
    public RepositorioAlertaJSON() {
        this.mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        cargar();
    }
    
    /// Singleton
    public static RepositorioAlertaJSON getInstance() {
        if (instancia == null) {
            instancia = new RepositorioAlertaJSON();
        }
        return instancia;
    }
    
    @Override
    public void save(Alerta alerta) {
        alertas.add(alerta);
        guardar();
    }

    @Override
    public void delete(Alerta alerta) {
    	alertas.remove(alerta);
        guardar();
    }
    
    @Override
    public void modify(Alerta alerta) {
        // No se permite
    }
    
    @Override
    public ObservableList<Alerta> findAll() {
        return alertas;
    }
    
    private void guardar() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, alertas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void cargar() {
        if (fichero.exists()) {
            try {
                List<Alerta> lista = mapper.readValue(fichero, new TypeReference<List<Alerta>>() {});
                alertas.setAll(lista);
            } catch (IOException e) {
                alertas.clear();
                guardar();
                e.printStackTrace();
            }
        }
    }
}
