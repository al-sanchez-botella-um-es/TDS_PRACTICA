package umu.tds.vista;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

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

    @FXML
    void añadirGastoParticipante(ActionEvent event) {

    }

    @FXML
    void cancelarAñadirParticipante(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert BotonañadirGastoParticipante != null : "fx:id=\"BotonañadirGastoParticipante\" was not injected: check your FXML file 'VentanaAñadirParticipantePorDefecto.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'VentanaAñadirParticipantePorDefecto.fxml'.";
        assert cantidadGastoParticipante != null : "fx:id=\"cantidadGastoParticipante\" was not injected: check your FXML file 'VentanaAñadirParticipantePorDefecto.fxml'.";
        assert elegirParticipantes != null : "fx:id=\"elegirParticipantes\" was not injected: check your FXML file 'VentanaAñadirParticipantePorDefecto.fxml'.";

    }

}
