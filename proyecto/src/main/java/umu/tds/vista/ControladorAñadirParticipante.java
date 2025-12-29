package umu.tds.vista;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import umu.tds.controlador.Controlador;

public class ControladorAñadirParticipante {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BotonañadirParticipante;

    @FXML
    private Button cancel;

    @FXML
    private TextField cantidadDinero;

    @FXML
    private TextField cantidadPorcentaje;
    @FXML
    private TextField nombreParticipante;
    private Controlador controladorApp;
    private ControladorVentanaPrincipal controladorVentanaPrincipal;
    
    private boolean guardado = false;
    
    public void setControladorApp(Controlador controlador) {
        this.controladorApp = controlador;
    }
    
    public void setControladorPrincipal(ControladorVentanaPrincipal controlador) {
    	this.controladorVentanaPrincipal = controlador;
    }
    
    @FXML
    void añadirParticipante(ActionEvent event) {

    }

    @FXML
    void cancelarAñadirParticipante(ActionEvent event) {

    }

    /**TODO
     * ESTO ES DEL POR DEFECTO Y PORCENTAJE NO POR DEFECTO*/
    @FXML
    void initialize() {
        assert BotonañadirParticipante != null : "fx:id=\"BotonañadirParticipante\" was not injected: check your FXML file 'VentanaAñadirParticipantePorDefecto.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'VentanaAñadirParticipantePorDefecto.fxml'.";
        assert nombreParticipante != null : "fx:id=\"nombreParticipante\" was not injected: check your FXML file 'VentanaAñadirParticipantePorDefecto.fxml'.";
        assert cantidadPorcentaje != null : "fx:id=\"cantidadPorcentaje\" was not injected: check your FXML file 'VentanaAñadirParticipante.fxml'.";
    }




}
