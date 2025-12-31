package umu.tds.repository.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import umu.tds.modelo.Gasto;
import umu.tds.repository.Repositorio;

public class RepositorioCuentaCompartidaJSON implements Repositorio<Gasto>{
	
	//ATRIBUTOS
	private File fichero;
    private ObjectMapper mapper;
    private ObservableList<Gasto> gastos;
    private static RepositorioCuentaCompartidaJSON instancia;
    //CONSTRUCTOR
    public RepositorioCuentaCompartidaJSON() {
        this.fichero = new File("cuentacompartida.json");
        this.gastos = FXCollections.observableArrayList();
        
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule()); // Para manejar LocalDate
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        cargar(); // Intentar cargar datos existentes al iniciar
    }
    
    //FUNCIONALIDAD
    public static RepositorioCuentaCompartidaJSON getInstance() {
		if (instancia == null) {
			instancia = new RepositorioCuentaCompartidaJSON();
		}
		return instancia;
	}
    
	@Override
	public void save(Gasto instancia) {
		guardar();
		
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
                List<Gasto> lista = mapper.readValue(fichero, new TypeReference<List<Gasto>>() {});
                gastos.setAll(lista); 	//cargar en ObservableList
            } catch (IOException e) {
            	gastos.clear();
            	guardar();
                e.printStackTrace();
            }
        }
    }
    
	@Override
	public void delete(Gasto instancia) {
		// no se utiliza
		
	}

	@Override
	public ObservableList<Gasto> findAll() {
		// no se utiliza
		return null;
	}

	@Override
	public void modify(Gasto instancia) {
		// no se utiliza
		
	}

}
