package umu.tds.vista;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import umu.tds.controlador.Controlador;
import umu.tds.modelo.Participante;

public class ControladorAñadirParticipante {
    @FXML private ResourceBundle resources;
    @FXML private Button BotonTerminarAñadirParticipantes;
    @FXML private URL location;
    @FXML private Button BotonañadirParticipante;
    @FXML private Button cancel;
    @FXML private TextField cantidadDinero;
    @FXML private TextField cantidadPorcentaje;
    @FXML private TextField nombreParticipante;

    private Controlador controladorApp;
    private ControladorVentanaPrincipal controladorVentanaPrincipal;

    private List<String> añadidosEnEstaSesion = new ArrayList<>();
    private boolean guardado = false;
    private boolean haTerminado = false;

    public void setControladorApp(Controlador controlador) {
        this.controladorApp = controlador;
    }

    public void setControladorPrincipal(ControladorVentanaPrincipal controlador) {
        this.controladorVentanaPrincipal = controlador;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public boolean isHaTerminado() {
        return haTerminado;
    }

    @FXML
    public void TerminarAñadirParticipantes(ActionEvent event) {
    	if (cantidadPorcentaje != null) {
            double sumaActual = 0.0;
            List<Participante> actuales = controladorApp.getCuentaCompartida().getParticipantes();
            
            for (Participante p : actuales) {
                sumaActual += p.getPorcentaje();
            }
	        if (Math.abs(sumaActual - 1.0) > 0.001) {
	            double sumaBonita = Math.round(sumaActual * 10000.0) / 100.0;
	            controladorVentanaPrincipal.mostrarEnTerminal("ERROR: La suma actual es " + sumaBonita + "%. Debe ser 100%.");
	            return;
	        }
    	}
        controladorApp.getCuentaCompartida().fijarParticipantes();
        controladorApp.guardarCuentaCompartida();

        this.haTerminado = true;
        añadidosEnEstaSesion.clear();
        cerrarVentana();
    }

    @FXML
    void añadirParticipante(ActionEvent event) {
        if (controladorApp.getCuentaCompartida().isParticipantesFijados()) {
            controladorVentanaPrincipal.mostrarEnTerminal(
                "ERROR: La cuenta ya está fijada. No se pueden añadir más participantes."
            );
            return;
        }
        String nombre = nombreParticipante.getText();
        if (nombre == null || nombre.isEmpty()) {
            controladorVentanaPrincipal.mostrarEnTerminal("ERROR: El nombre no puede estar vacío.");
            return;
        }
        double porcentajeDecimal = 0.0;		//Valor por defecto
        if (cantidadPorcentaje != null && !cantidadPorcentaje.getText().isEmpty()) {
            try {
                double porcInput = Double.parseDouble(cantidadPorcentaje.getText());

                if (porcInput < 0 || porcInput > 100) {
                    controladorVentanaPrincipal.mostrarEnTerminal("ERROR: El porcentaje debe estar entre 0 y 100.");
                    return;
                }
                porcentajeDecimal = porcInput / 100.0;
                double sumaActual = 0.0;
                List<Participante> actuales = controladorApp.getCuentaCompartida().getParticipantes();
                for (Participante p : actuales) {
                    sumaActual += p.getPorcentaje();
                }
                if (sumaActual + porcentajeDecimal > 1.001) {
                    double restante = Math.round((1.0 - sumaActual) * 10000.0) / 100.0;
                    controladorVentanaPrincipal.mostrarEnTerminal("ERROR: El total supera el 100%. Solo queda disponible un " + restante + "%.");
                    cantidadPorcentaje.clear();
                    return;
                }
            } catch (NumberFormatException e) {
                controladorVentanaPrincipal.mostrarEnTerminal("ERROR: Porcentaje inválido (ej: 25.5).");
                cantidadPorcentaje.clear();
                return;
            }
        }
        controladorApp.getCuentaCompartida().addParticipante(nombre, 0.0);
        añadidosEnEstaSesion.add(nombre);
        
        if (porcentajeDecimal > 0) {
            controladorApp.getCuentaCompartida().getParticipante(nombre).setPorcentaje(porcentajeDecimal);
        }

        controladorApp.guardarCuentaCompartida();
        this.guardado = true;

        String textoPorcentaje = (porcentajeDecimal > 0)
                ? " (" + (porcentajeDecimal * 100) + "%)"
                : " (Por defecto)";

        controladorVentanaPrincipal.mostrarEnTerminal("Participante añadido: " + nombre + textoPorcentaje);
        nombreParticipante.clear();
        if (cantidadPorcentaje != null) cantidadPorcentaje.clear(); 
        nombreParticipante.requestFocus();
    }

    private void revertirCambios() {
        if (!añadidosEnEstaSesion.isEmpty()) {
            for (String nombre : añadidosEnEstaSesion) {
                controladorApp.getCuentaCompartida().removeParticipante(nombre);
            }
            controladorApp.guardarCuentaCompartida();
            controladorVentanaPrincipal.mostrarEnTerminal("Operación cancelada. Se han deshecho los cambios.");
        }
    }

    public void cerrarPorLaX() {
        revertirCambios();
    }

    @FXML
    void cancelarAñadirParticipante(ActionEvent event) {
        this.haTerminado = false;
        revertirCambios();
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) BotonañadirParticipante.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
    	 assert BotonañadirParticipante != null : "fx:id=\"BotonañadirParticipante\" was not injected: check your FXML file 'VentanaAñadirParticipantePorDefecto.fxml'.";
         assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'VentanaAñadirParticipantePorDefecto.fxml'.";
         assert nombreParticipante != null : "fx:id=\"nombreParticipante\" was not injected: check your FXML file 'VentanaAñadirParticipantePorDefecto.fxml'.";
         assert cantidadPorcentaje != null : "fx:id=\"cantidadPorcentaje\" was not injected: check your FXML file 'VentanaAñadirParticipante.fxml'.";
         assert BotonTerminarAñadirParticipantes != null : "fx:id=\"BotonTerminarAñadirParticipantes\" was not injected: check your FXML file 'VentanaAñadirParticipante.fxml'.";
    }
}
