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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import umu.tds.controlador.Controlador;
import umu.tds.modelo.Gasto;
import javafx.scene.control.cell.PropertyValueFactory; // Necesario para TableView

public class ControladorModificarGasto {
	@FXML private ResourceBundle resources;
	@FXML private URL location;
	@FXML private Button BotonCancelar;
	@FXML private Button BotonModificar;
	// Elementos del TableView de tu FXML
	@FXML private TableView<Gasto> tablaModificarGastos;
	@FXML private TableColumn<Gasto, String> TablaNombre;
	@FXML private TableColumn<Gasto, String> TablaCantidad;
	@FXML private TableColumn<Gasto, String> TablaCategoria;
	@FXML private TableColumn<Gasto, String> TablaFecha;
	@FXML private TextArea terminal;
	private ControladorVentanaPrincipal controladorVentanaPrincipal;
	private Controlador controladorApp;
	
	public void setControladorApp(Controlador controlador) {
        this.controladorApp = controlador;
    }
	
	public void setControladorPrincipal(ControladorVentanaPrincipal controlador) {
    	this.controladorVentanaPrincipal = controlador;
    }
	
	// --- TERMINAL ---
    public void cargarGastos() {
        if(controladorApp != null) {
            tablaModificarGastos.setItems(controladorApp.getGastos());
        }
    }
    
    private ControladorModificarGasto_2 abrirVentanaEdicion(Gasto gasto) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/umu/tds/VentanaModificarGasto_2.fxml"));
        Parent root = loader.load();
        
        ControladorModificarGasto_2 controlador = loader.getController();
        controlador.setControladorApp(controladorApp);
        controlador.setControladorPrincipal(controladorVentanaPrincipal);
        controlador.initData(gasto);
        
        Stage stage = new Stage();
        stage.setTitle("Modificar Gasto: " + gasto.getNombre());
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(BotonModificar.getScene().getWindow());
        stage.showAndWait();
        
        return controlador;
    }

    
    // --- EVENTOS ---
    @FXML
    void cancelarOperacion(ActionEvent event) {
        // Asumiendo que quieres limpiar la selección de la tabla, no de la lista
        tablaModificarGastos.getSelectionModel().clearSelection();
        controladorVentanaPrincipal.mostrarEnTerminal("Operación de selección cancelada.");
    }
    
    @FXML
    void cancelar(ActionEvent event) {
        // Si este botón está en la vista, puede usarse para limpiar la selección o cerrar la pestaña
        cancelarOperacion(event);
    }
    
    @FXML
    void modificarGasto(ActionEvent event) {
        // CRÍTICO: Usamos el TableView para obtener la selección.
        Gasto gastoSeleccionado = tablaModificarGastos.getSelectionModel().getSelectedItem();
        if (gastoSeleccionado != null) {
            try {
            	controladorVentanaPrincipal.mostrarEnTerminal("Abriendo ventana de edición...");
                ControladorModificarGasto_2 controlador = abrirVentanaEdicion(gastoSeleccionado);
                if (controlador.seHaGuardado()) {
                	controladorApp.modifyGasto(gastoSeleccionado);	//delegamos la funcionalidad
                    recargarTabla();
                    controladorVentanaPrincipal.mostrarEnTerminal("Gasto modificado y tabla actualizada.");
                } else {
                	controladorVentanaPrincipal.mostrarEnTerminal("Modificación cancelada.");
                }
                controladorVentanaPrincipal.mostrarEnTerminal("Operación de edición finalizada.");
            } catch (IOException e) {
                e.printStackTrace();
                controladorVentanaPrincipal.mostrarEnTerminal("ERROR: Fallo al cargar la ventana de modificación. " + e.getMessage());
            }
        } else {
        	controladorVentanaPrincipal.mostrarEnTerminal("ADVERTENCIA: Selecciona un gasto para modificar.");
        }
    }
    
    /** Recarga los datos en el TableView. */
    public void recargarTabla() {
        // Asegúrate de usar el método setItems del TableView
        tablaModificarGastos.setItems(controladorApp.getGastos());
        tablaModificarGastos.refresh();
    }
    
    @FXML
    void initialize() {
        assert BotonCancelar != null : "fx:id=\"BotonCancelar\" was not injected: check your FXML file 'VentanaModificarGasto.fxml'.";
        assert BotonModificar != null : "fx:id=\"BotonModificar\" was not injected: check your FXML file 'VentanaModificarGasto.fxml'.";
        assert TablaCantidad != null : "fx:id=\"TablaCantidad\" was not injected: check your FXML file 'VentanaModificarGasto.fxml'.";
        assert TablaNombre != null : "fx:id=\"TablaNombre\" was not injected: check your FXML file 'VentanaModificarGasto.fxml'.";
        assert tablaModificarGastos != null : "fx:id=\"tablaModificarGastos\" was not injected: check your FXML file 'VentanaModificarGasto.fxml'.";
        assert TablaCategoria != null : "fx:id=\"TablaCategoria\" was not injected: check your FXML file 'VentanaModificarGasto.fxml'.";
        assert TablaFecha != null : "fx:id=\"TablaFecha\" was not injected: check your FXML file 'VentanaModificarGasto.fxml'.";

        // Configurar cómo se llenan las columnas del TableView
        TablaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TablaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        TablaCategoria.setCellValueFactory(cellData -> {
            // 1. Obtenemos el Gasto de la fila
            Gasto gasto = cellData.getValue();
            // 2. Comprobamos si tiene categoría para evitar errores si es null
            if (gasto.getCategoria() != null) {
                // Devolvemos el nombre de la categoría envuelto en una propiedad de JavaFX
                return new javafx.beans.property.SimpleStringProperty(gasto.getCategoria().getNombre());
            } else {
                return new javafx.beans.property.SimpleStringProperty("Sin Categoría");
            }
        });
        TablaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha")); 
        //listaGastos.setItems((ObservableList<Gasto>) repositorio.findAll()); 	//se vincula directamente
    }
}