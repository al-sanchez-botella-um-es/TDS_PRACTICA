package umu.tds.repository.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import umu.tds.modelo.Categoria;
import umu.tds.repository.Repositorio;

public class RepositorioCategoriaJSON implements Repositorio<Categoria> {
	private static RepositorioCategoriaJSON instancia;
	private final File fichero = new File("categorias.json");
    private final ObjectMapper mapper;
    private final ObservableList<Categoria> categorias = FXCollections.observableArrayList();
    
    public RepositorioCategoriaJSON() {
        this.mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        cargar();
        cargarCategoriasPredefinidas();
    }
    
    /// Singleton
    public static RepositorioCategoriaJSON getInstance() {
        if (instancia == null) {
            instancia = new RepositorioCategoriaJSON();
        }
        return instancia;
    }
    
    public void cargarCategoriasPredefinidas() {
    	List<String> nombres = List.of("Alimentación", "Transporte", "Entretenimiento");
    	for (String nombre : nombres) {
    		boolean existe = categorias.stream()
    				.anyMatch(c -> c.getNombre().equalsIgnoreCase(nombre));
    		if (!existe) {
    			categorias.add(new Categoria(nombre));
    		}
    	}
    }
    
    @Override
    public void save(Categoria categoria) {
        categorias.add(categoria);
        guardar();
    }

    // No existe la opción de borrar categorias
    @Override
    public void delete(Categoria categoria) {
        categorias.remove(categoria);
        guardar();
    }
    
    @Override
    public void modify(Categoria categoria) {
        // No se permite
    }
    
    @Override
    public ObservableList<Categoria> findAll() {
        return categorias;
    }
    
    private void guardar() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, categorias);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void cargar() {
        if (fichero.exists()) {
            try {
                List<Categoria> lista = mapper.readValue(fichero, new TypeReference<List<Categoria>>() {});
                categorias.setAll(lista);
            } catch (IOException e) {
            	categorias.clear();
            	guardar();
                e.printStackTrace();
            }
        }
    }
}
