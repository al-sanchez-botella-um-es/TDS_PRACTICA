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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import umu.tds.controlador.Controlador;
import umu.tds.modelo.CuentaCompartida;
import umu.tds.modelo.Gasto;
import umu.tds.modelo.Participante;

public class ControladorCuentaCompartida {
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button BotonAddGasto;
    @FXML private Button BotonAddParticipantes;
    
    @FXML private CheckBox BotonAsignarDefecto;
    @FXML private TableView<Participante> tablaCuentaCompartida; 
    @FXML private TableColumn<Participante, String> NombrePersonaTabla; 
    @FXML private TableColumn<Participante, String> PorcentajePersonaTabla;
    @FXML private TableColumn<Participante, String> TotalPersonaTabla; 
    private ControladorVentanaPrincipal controladorVentanaPrincipal;
    private Controlador controladorApp;
    private boolean terminar = false;
    public boolean isTerminar() {
		return terminar;
	}
    public void setControladorPrincipal(ControladorVentanaPrincipal controlador) {
        this.controladorVentanaPrincipal = controlador;
    }
    
    public void setControladorApp(Controlador controlador) {
        this.controladorApp = controlador;
    }

    private ControladorAñadirParticipante abrirVentanaAñadirParticipante() throws IOException {
       	
    	FXMLLoader loader = null;
    	if(!BotonAsignarDefecto.isSelected())
    			loader = new FXMLLoader(getClass().getResource("/umu/tds/VentanaAñadirParticipante.fxml"));
    	else {
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
        //si lo cerramos con la x
        stage.setOnCloseRequest(event -> {
            controlador.cerrarPorLaX();
            });
        stage.showAndWait();
        
        return controlador;
    }
    
    private ControladorAñadirGastoParticipante abrirVentanaAñadirGastpParticipante() throws IOException {
       	
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
        if (controladorApp == null) return;

        // Cogemos la lista directamente del modelo
        controladorApp.getCuentaCompartida().actualizarBalances();

        // cargamos la lista
        List<Participante> listaModelo = controladorApp.getCuentaCompartida().getParticipantes();
     // La convertimos a ObservableList y la metemos en la tabla
        tablaCuentaCompartida.setItems(FXCollections.observableArrayList(listaModelo));
        tablaCuentaCompartida.refresh();
        
    }
    
    @FXML
    void addGasto(ActionEvent event) {
    	try {
			abrirVentanaAñadirGastpParticipante();
			if (BotonAsignarDefecto.isSelected()) {
                controladorApp.getCuentaCompartida().asignarPorcentajePorDefecto();
                
            }
			refrescarTabla();
		} catch (IOException e) {
			System.out.println("No se puede abrir la ventana de añadir gasto de participante");
		}
		
    }

    @FXML
    void addParticipantes(ActionEvent event) {
    	if(terminar == true) {
			System.out.println("No se pueden añadir mas participantes");
			return;
		}else {
    	try {
    		ControladorAñadirParticipante ctrl = abrirVentanaAñadirParticipante();
    		if (ctrl.isHaTerminado()) {
                this.terminar = true; 
                
                //Desactivar visualmente para que no s pueda volver a utilizar
                BotonAddParticipantes.setDisable(true);
    		}else {
    			System.out.println("Operación cancelada, se puede volver a intentar.");
    		}
		} catch (IOException e) {
			System.out.println("No se puede abrir la ventana de añadir participantes");
		}
		}
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
       
        
        NombrePersonaTabla.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        PorcentajePersonaTabla.setCellValueFactory(new PropertyValueFactory<>("porcentajeVisual"));
        TotalPersonaTabla.setCellValueFactory(new PropertyValueFactory<>("balanceTexto"));//LO QUE CADA UNO DEBE O LE DEBEN
        //LO QUE HA PAGADO CADA UNO TotalPersonaTabla.setCellValueFactory(new PropertyValueFactory<>("saldo"));
    }

}
