package umu.tds.repository.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import umu.tds.modelo.Gasto;
import umu.tds.repository.Repositorio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RepositorioGastoJSON implements Repositorio<Gasto> {
    private static RepositorioGastoJSON instancia;
	private final File fichero = new File("gastos.json");
    private final ObjectMapper mapper;
    private final ObservableList<Gasto> gastos = FXCollections.observableArrayList();
    
    public RepositorioGastoJSON() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // soporte para LocalDate
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        cargar();
    }
    
    /// Singleton
    public static RepositorioGastoJSON getInstance() {
        if (instancia == null) {
            instancia = new RepositorioGastoJSON();
        }
        return instancia;
    }

    @Override
    public void save(Gasto gasto) {
    	if (!gastos.contains(gasto)) {
            gastos.add(gasto);
        }
        guardar();
    }

    @Override
    public void delete(Gasto gasto) {
        gastos.remove(gasto);
        guardar();
    }
    
    @Override
    public ObservableList<Gasto> findAll() {
        return gastos;
    }

    private void guardar() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, gastos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void cargar() {
        if (fichero.exists()) {
            try {
            	//JSON no debe deserializar directamente un ObservableList
                List<Gasto> lista = mapper.readValue(fichero, new TypeReference<List<Gasto>>() {});
                gastos.setAll(lista); 	//cargar en ObservableList
            } catch (IOException e) {
            	gastos.clear();
            	guardar();
                e.printStackTrace();
            }
        }
    }
}