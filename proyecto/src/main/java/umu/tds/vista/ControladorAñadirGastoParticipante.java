package umu.tds.vista;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import umu.tds.controlador.Controlador;
import umu.tds.modelo.Participante;

public class ControladorAñadirGastoParticipante {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BotonañadirGastoParticipante;

    @FXML
    private Button cancel;

    @FXML
    private TextField cantidadGastoParticipante;

    @FXML
    private ChoiceBox<String> elegirParticipantes;
    private Controlador controladorApp;
    private ControladorVentanaPrincipal controladorVentanaPrincipal;  
    public void setControladorApp(Controlador controlador) {
        this.controladorApp = controlador;
        cargarNombresEnChoiceBox();
    }
   private void cargarNombresEnChoiceBox() {
        // Limpiamos por si acaso
        elegirParticipantes.getItems().clear();
        
        // Pedimos la lista de participantes al modelo y sacamos solo los nombres
        if (controladorApp != null && controladorApp.getCuentaCompartida() != null) {
            for (Participante p : controladorApp.getCuentaCompartida().getParticipantes()) {
                elegirParticipantes.getItems().add(p.getNombre());
            }
        }
   }
    
        public void setControladorPrincipal(ControladorVentanaPrincipal controlador) {
    	this.controladorVentanaPrincipal = controlador;
    }
        
    @FXML
    void añadirGastoParticipante(ActionEvent event) {
    	String participante = elegirParticipantes.getValue();
    	if (participante == null) {
            controladorVentanaPrincipal.mostrarEnTerminal("ERROR: Debe seleccionar un participante");
            return;
        }
		String cantidad = cantidadGastoParticipante.getText();
		double cantidadGasto;
		try {
		    cantidadGasto = Double.parseDouble(cantidad);
		    if (cantidadGasto <= 0) {
		    	controladorVentanaPrincipal.mostrarEnTerminal("ERROR: la cantidad debe ser positiva");
		    	return;
		    }
		} catch (NumberFormatException e) {
			controladorVentanaPrincipal.mostrarEnTerminal("ERROR: la cantidad es inválida (ej: 23.45)");
		    return;
		}
		controladorApp.addGastoParticipante(participante, cantidadGasto);
        
        controladorVentanaPrincipal.mostrarEnTerminal("Gasto registrado para " + participante + ": " + cantidadGasto + " €");
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) BotonañadirGastoParticipante.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void cancelarAñadirParticipante(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    	stage.close();
    }

    @FXML
    void initialize() {
        assert BotonañadirGastoParticipante != null : "fx:id=\"BotonañadirGastoParticipante\" was not injected: check your FXML file 'VentanaAñadirParticipantePorDefecto.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'VentanaAñadirParticipantePorDefecto.fxml'.";
        assert cantidadGastoParticipante != null : "fx:id=\"cantidadGastoParticipante\" was not injected: check your FXML file 'VentanaAñadirParticipantePorDefecto.fxml'.";
        assert elegirParticipantes != null : "fx:id=\"elegirParticipantes\" was not injected: check your FXML file 'VentanaAñadirParticipantePorDefecto.fxml'.";

    }

}
