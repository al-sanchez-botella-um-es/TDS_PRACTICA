package umu.tds.vista;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import umu.tds.controlador.Controlador;
import umu.tds.modelo.Categoria;
import umu.tds.modelo.Gasto;

public class ControladorModificarGasto_2 {
	private Gasto gastoAEditar;
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button modificar_2;
    @FXML private Button cancel;
    @FXML private TextField cantidadGasto;
    @FXML private ComboBox<Categoria> categoriasCreadas;
    @FXML private DatePicker fechaGasto;
    @FXML private Button newCategoria;
    @FXML private TextField nombreGasto;
    @FXML private ListView<Gasto> listaGastos;
    //PARA CAMBIAR DE VENTANA Y PODER MODIFICARLO
    private Controlador controladorApp;
    private ControladorVentanaPrincipal controladorVentanaPrincipal;
    
    private boolean guardado = false;
    
    public void setControladorApp(Controlador controlador) {
        this.controladorApp = controlador;
    }
    
    public void setControladorPrincipal(ControladorVentanaPrincipal controlador) {
    	this.controladorVentanaPrincipal = controlador;
    }
    
    public boolean seHaGuardado() {
    	return guardado;
    }
    
    //PARA INICIALIZAR
    public void initData(Gasto gasto) {
        this.gastoAEditar = gasto;
        nombreGasto.setText(gasto.getNombre());
        cantidadGasto.setText(String.valueOf(gasto.getCantidad()));
        fechaGasto.setValue(gasto.getFecha());
        categoriasCreadas.getItems().setAll(controladorApp.getCategorias());
        if (gasto.getCategoria() != null) {
            categoriasCreadas.getSelectionModel().select(gasto.getCategoria());
        }
    }
        
    @FXML
    void modificarGasto_2(ActionEvent event) {
    	if (gastoAEditar != null) {
            try {
                String nuevoNombre = nombreGasto.getText();
                double nuevaCantidad = Double.parseDouble(cantidadGasto.getText());
                LocalDate nuevaFecha = fechaGasto.getValue();
                Categoria nuevaCategoria = categoriasCreadas.getSelectionModel().getSelectedItem();
                gastoAEditar.setNombre(nuevoNombre);
                gastoAEditar.setCantidad(nuevaCantidad);
                gastoAEditar.setFecha(nuevaFecha);
                if (nuevaCategoria != null) {
                    gastoAEditar.setCategoria(nuevaCategoria);
                }
                controladorApp.modifyGasto(gastoAEditar);
                guardado = true;
                // Cerrar la ventana de modificación
                cancelarModifGasto_2(event); // Reutilizamos el método para cerrar la ventana
            } catch (NumberFormatException e) {
                // Manejar error si la cantidad no es un número válido
                System.err.println("Error: La cantidad debe ser un número válido.");
            }
        }
    }
    

    public void mostrarEnTerminal(String texto) {
        controladorVentanaPrincipal.mostrarEnTerminal(texto);
    }
    
    @FXML
    void cancelarModifGasto_2(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    void crearNuevaCategoria(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nueva categoría");
        dialog.setHeaderText("Crear nueva categoría");
        dialog.setContentText("Nombre:");
        
        dialog.showAndWait().ifPresent(nombre -> {
            if (!nombre.trim().isEmpty()) {
                Categoria nuevaCategoria = controladorApp.addCategoria(nombre);
                categoriasCreadas.getItems().setAll(controladorApp.getCategorias());
                //Seleccionarla automáticamente para comodidad del usuario
                categoriasCreadas.getSelectionModel().select(nuevaCategoria);
            }
        });
    }
    
    public void cargarGastos() {
    	if(controladorApp != null) {
    		listaGastos.setItems((ObservableList<Gasto>) controladorApp.getGastos());
    	}
    }

    @FXML
    void initialize() {
        assert modificar_2 != null : "fx:id=\"add\" was not injected: check your FXML file 'VentanaModificarGasto_2.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'VentanaModificarGasto_2.fxml'.";
        assert cantidadGasto != null : "fx:id=\"cantidadGasto\" was not injected: check your FXML file 'VentanaModificarGasto_2.fxml'.";
        assert categoriasCreadas != null : "fx:id=\"categoriasCreadas\" was not injected: check your FXML file 'VentanaModificarGasto_2.fxml'.";
        assert fechaGasto != null : "fx:id=\"fechaGasto\" was not injected: check your FXML file 'VentanaModificarGasto_2.fxml'.";
        assert newCategoria != null : "fx:id=\"newCategoria\" was not injected: check your FXML file 'VentanaModificarGasto_2.fxml'.";
        assert nombreGasto != null : "fx:id=\"nombreGasto\" was not injected: check your FXML file 'VentanaModificarGasto_2.fxml'.";
        
    }
}