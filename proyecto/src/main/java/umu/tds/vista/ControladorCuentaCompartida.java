package umu.tds.vista;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import umu.tds.controlador.Controlador;
import umu.tds.modelo.CuentaCompartida;
import umu.tds.modelo.Participante;

public class ControladorCuentaCompartida {
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private ComboBox<String> selectorCuentas;
    @FXML private Button BotonNuevaCuenta;
    @FXML private Button BotonAddGasto;
    @FXML private Button BotonAddParticipantes;
    @FXML private CheckBox BotonAsignarDefecto;

    @FXML private TableView<Participante> tablaCuentaCompartida;
    @FXML private TableColumn<Participante, String> NombrePersonaTabla;
    @FXML private TableColumn<Participante, String> PorcentajePersonaTabla;
    @FXML private TableColumn<Participante, String> TotalPersonaTabla;

    private ControladorVentanaPrincipal controladorVentanaPrincipal;
    private Controlador controladorApp;

    public void setControladorPrincipal(ControladorVentanaPrincipal controlador) {
        this.controladorVentanaPrincipal = controlador;
    }

    public void setControladorApp(Controlador controlador) {
        this.controladorApp = controlador;
        cargarCuentas();
        refrescarTabla();
    }

    private void cargarCuentas() {
        List<CuentaCompartida> cuentas = controladorApp.getCuentas();

        selectorCuentas.setItems(FXCollections.observableArrayList(
                cuentas.stream().map(CuentaCompartida::getNombre).toList()
        ));

        if (controladorApp.getCuentaCompartida() != null) {
            selectorCuentas.setValue(controladorApp.getCuentaCompartida().getNombre());
        }

        boolean hayCuenta = controladorApp.getCuentaCompartida() != null;
        BotonAddParticipantes.setDisable(!hayCuenta);
        BotonAddGasto.setDisable(!hayCuenta);
    }

    @FXML
    void cambiarCuenta(ActionEvent event) {
        String nombre = selectorCuentas.getValue();
        if (nombre == null) return;
        controladorApp.seleccionarCuentaCompartida(nombre);
        refrescarTabla();
        BotonAddParticipantes.setDisable(
                controladorApp.getCuentaCompartida().isParticipantesFijados()
        );
    }

    @FXML
    void crearCuenta(ActionEvent event) {
        String nombre = controladorVentanaPrincipal.pedirTexto("Nombre de la nueva cuenta:");
        if (nombre == null || nombre.isBlank()) return;

        controladorApp.crearCuentaCompartida(nombre);
        cargarCuentas();
        selectorCuentas.setValue(nombre);
        refrescarTabla();
    }

    private ControladorAñadirParticipante abrirVentanaAñadirParticipante() throws IOException {
        FXMLLoader loader = null;
        if (!BotonAsignarDefecto.isSelected()) {
        	loader = new FXMLLoader(getClass().getResource("/umu/tds/VentanaAñadirParticipante.fxml"));
        } else {
        	loader = new FXMLLoader(getClass().getResource("/umu/tds/VentanaAñadirParticipantePorDefecto.fxml"));
        }
        Parent root = loader.load();

        ControladorAñadirParticipante controlador = loader.getController();
        controlador.setControladorApp(controladorApp);
        controlador.setControladorPrincipal(controladorVentanaPrincipal);

        Stage stage = new Stage();
        stage.setTitle("Añadir Participante");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(BotonAddParticipantes.getScene().getWindow());
        stage.setOnCloseRequest(event -> controlador.cerrarPorLaX());
        stage.showAndWait();

        return controlador;
    }

    private ControladorAñadirGastoParticipante abrirVentanaAñadirGastoParticipante() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/umu/tds/VentanaAñadirGastoParticipante.fxml"));
        Parent root = loader.load();

        ControladorAñadirGastoParticipante controlador = loader.getController();
        controlador.setControladorApp(controladorApp);
        controlador.setControladorPrincipal(controladorVentanaPrincipal);

        Stage stage = new Stage();
        stage.setTitle("Añadir Gasto de Participante");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(BotonAddParticipantes.getScene().getWindow());
        stage.showAndWait();

        return controlador;
    }

    private void refrescarTabla() {
        if (controladorApp == null || controladorApp.getCuentaCompartida() == null) {
            tablaCuentaCompartida.setItems(FXCollections.observableArrayList());
            return;
        }

        controladorApp.getCuentaCompartida().actualizarBalances();

        List<Participante> listaModelo = controladorApp.getCuentaCompartida().getParticipantes();
        tablaCuentaCompartida.setItems(FXCollections.observableArrayList(listaModelo));
        tablaCuentaCompartida.refresh();
    }

    @FXML
    void addGasto(ActionEvent event) {
        if (controladorApp.getCuentaCompartida() == null) {
            controladorVentanaPrincipal.mostrarEnTerminal("Primero selecciona o crea una cuenta.");
            return;
        }
        try {
            abrirVentanaAñadirGastoParticipante();
            if (BotonAsignarDefecto.isSelected()) {
                controladorApp.getCuentaCompartida().asignarPorcentajePorDefecto();
            }
            controladorApp.guardarCuentaCompartida();
            refrescarTabla();
        } catch (IOException e) {
            System.out.println("No se puede abrir la ventana de añadir gasto de participante");
        }
    }

    @FXML
    void addParticipantes(ActionEvent event) {
        if (controladorApp.getCuentaCompartida() == null) {
            controladorVentanaPrincipal.mostrarEnTerminal("Primero selecciona o crea una cuenta.");
            return;
        }
        if (controladorApp.getCuentaCompartida().isParticipantesFijados()) {
            controladorVentanaPrincipal.mostrarEnTerminal("No se pueden añadir más participantes. La cuenta ya está fijada.");
            return;
        }
        try {
            ControladorAñadirParticipante ctrl = abrirVentanaAñadirParticipante();
            if (ctrl.isHaTerminado()) {
                BotonAddParticipantes.setDisable(true);
                refrescarTabla();
            }

        } catch (IOException e) {
            System.out.println("No se puede abrir la ventana de añadir participantes");
        }
    }
    
    @FXML
    void asignarDefecto(ActionEvent event) {
        // No hace nada, el comportamiento se gestiona en addGasto()
    } 

    @FXML
    void initialize() {
    	assert BotonAddGasto != null : "fx:id=\"BotonAddGasto\" was not injected: check your FXML file 'VentanaCuentaCompartida.fxml'.";
        assert BotonAddParticipantes != null : "fx:id=\"BotonAddParticipantes\" was not injected: check your FXML file 'VentanaCuentaCompartida.fxml'.";
        assert BotonAsignarDefecto != null : "fx:id=\"BotonAsignarDefecto\" was not injected: check your FXML file 'VentanaCuentaCompartida.fxml'.";
        assert NombrePersonaTabla != null : "fx:id=\"NombrePersonaTabla\" was not injected: check your FXML file 'VentanaCuentaCompartida.fxml'.";
        assert PorcentajePersonaTabla != null : "fx:id=\"PorcentajePersonaTabla\" was not injected: check your FXML file 'VentanaCuentaCompartida.fxml'.";
        assert TotalPersonaTabla != null : "fx:id=\"TotalPersonaTabla\" was not injected: check your FXML file 'VentanaCuentaCompartida.fxml'.";
        assert tablaCuentaCompartida != null : "fx:id=\"tablaCuentaCompartida\" was not injected: check your FXML file 'VentanaCuentaCompartida.fxml'.";
    	
        NombrePersonaTabla.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        PorcentajePersonaTabla.setCellValueFactory(new PropertyValueFactory<>("porcentajeVisual"));
        TotalPersonaTabla.setCellValueFactory(new PropertyValueFactory<>("balanceTexto"));
    }
}
