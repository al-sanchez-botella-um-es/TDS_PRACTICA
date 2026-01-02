package umu.tds.repository.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import umu.tds.modelo.CuentaCompartida;
import umu.tds.repository.Repositorio;

public class RepositorioCuentaCompartidaJSON implements Repositorio<CuentaCompartida> {

    private static RepositorioCuentaCompartidaJSON instancia;
    private final File fichero;
    private final ObjectMapper mapper;
    private List<CuentaCompartida> cuentas;

    private RepositorioCuentaCompartidaJSON() {
        this.fichero = new File("cuentasCompartidas.json");
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        cargar();
    }

    public static RepositorioCuentaCompartidaJSON getInstance() {
        if (instancia == null) {
            instancia = new RepositorioCuentaCompartidaJSON();
        }
        return instancia;
    }

    @Override
    public void save(CuentaCompartida instancia) {
        cuentas.add(instancia);
        guardar();
    }

    @Override
    public void delete(CuentaCompartida instancia) {
        cuentas.remove(instancia);
        guardar();
    }

    @Override
    public ObservableList<CuentaCompartida> findAll() {
        return FXCollections.observableArrayList(cuentas);
    }

    @Override
    public void modify(CuentaCompartida instancia) {
        guardar();
    }

    private void guardar() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, cuentas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargar() {
        if (fichero.exists()) {
            try {
                cuentas = mapper.readValue(
                    fichero,
                    mapper.getTypeFactory().constructCollectionType(List.class, CuentaCompartida.class)
                );
            } catch (IOException e) {
                e.printStackTrace();
                cuentas = new ArrayList<>();
                guardar();
            }
        } else {
            cuentas = new ArrayList<>();
            guardar();
        }
    }
}