package umu.tds.repository;

import javafx.collections.ObservableList;

public interface Repositorio<T> {
	void save(T instancia);          		// Crear o actualizar
    void delete(T instancia);        		// Eliminar
    ObservableList<T> findAll();     		// Recuperar todos
	void modify(T instancia);				// Modificar un gasto existente
}