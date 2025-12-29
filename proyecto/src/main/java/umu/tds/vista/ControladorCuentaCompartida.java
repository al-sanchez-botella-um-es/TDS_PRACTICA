package umu.tds.vista;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import umu.tds.controlador.Controlador;
import umu.tds.modelo.Gasto;

public class ControladorCuentaCompartida {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button BotonAddGasto;
    @FXML
    private Button BotonAddParticipantes;
    @FXML
    private CheckBox BotonAsignarDefecto;
    @FXML
    private TableColumn<Gasto, String> NombrePersonaTabla;
    @FXML
    private TableColumn<Gasto, String> PorcentajePersonaTabla;
    @FXML
    private TableColumn<Gasto, String> TotalPersonaTabla;
    @FXML
    private TableView<Gasto> tablaCuentaCompartida;
    private ControladorVentanaPrincipal controladorVentanaPrincipal;
    private Controlador controladorApp;

    public void setControladorPrincipal(ControladorVentanaPrincipal controlador) {
        this.controladorVentanaPrincipal = controlador;
    }
    
    public void setControladorApp(Controlador controlador) {
        this.controladorApp = controlador;
    }
    
    private ControladorAñadirParticipante abrirVentanaEdicion() throws IOException {
    	//TODO HAY QUE HACER UN IF PARA SABER SI ESTA MARCADO EL POR DEFECTO O NO Y DEPENDIENDO DE ESO SE ABRE UNA VENTANA O LA OTRA
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/umu/tds/Ventana.fxml"));
        Parent root = loader.load();
         
        ControladorAñadirParticipante controlador = loader.getController();
        controlador.setControladorApp(controladorApp);
        controlador.setControladorPrincipal(controladorVentanaPrincipal);
        Stage stage = new Stage();
        stage.setTitle("Añadir Participante");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(BotonAddParticipantes.getScene().getWindow());
        stage.showAndWait();
        
        return controlador;
    }
    
    @FXML
    void addGasto(ActionEvent event) {

    }

    @FXML
    void addParticipantes(ActionEvent event) {
    	
    }

    @FXML
    void asignarDefecto(ActionEvent event) {

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

    }

}
