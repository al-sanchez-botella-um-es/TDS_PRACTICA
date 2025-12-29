package umu.tds.vista;

import umu.tds.controlador.Controlador;
import umu.tds.modelo.Categoria;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

///En los controladores de Presentación habrá que inyectar el controlador de la aplicación,
/// el cual tendrá los repositorios y hará todas las funciones correspondientes de la
/// funcionalidad vista-modelo

public class ControladorAñadirGasto {
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button add;
    @FXML private Button cancel;
    @FXML private TextField cantidadGasto;
    @FXML private ComboBox<Categoria> categoriasCreadas;
    @FXML private DatePicker fechaGasto;
    @FXML private Button newCategoria;
    @FXML private TextField nombreGasto;
    private ControladorVentanaPrincipal controladorVentanaPrincipal;
    private Controlador controladorApp;
    
    public void setControladorPrincipal(ControladorVentanaPrincipal controlador) {
        this.controladorVentanaPrincipal = controlador;
    }
    
    public void setControladorApp(Controlador controlador) {
        this.controladorApp = controlador;
    }

    @FXML
    void addGasto(ActionEvent event) {
    	String nombre = nombreGasto.getText();
        String categoriaStr = categoriasCreadas.getValue().getNombre();
        String cantidadStr = cantidadGasto.getText();
        LocalDate fecha = fechaGasto.getValue();
        //Comprobamos que se han rellenado todos los campos correctamente
        if (nombre == null || nombre.isBlank()) {
        	controladorVentanaPrincipal.mostrarEnTerminal("ERROR: añade nombre al gasto");
        	return;
        }
        if (categoriaStr == null) {
        	controladorVentanaPrincipal.mostrarEnTerminal("ERROR: selecciona una categoría");
        	return;
        }
        if (fecha == null) {
        	controladorVentanaPrincipal.mostrarEnTerminal("ERROR: selecciona una fecha");
        	return;
        }
        double cantidad;
        try {
            cantidad = Double.parseDouble(cantidadStr);
            if (cantidad <= 0) {
            	controladorVentanaPrincipal.mostrarEnTerminal("ERROR: la cantidad debe ser positiva");
            	return;
            }
        } catch (NumberFormatException e) {
        	controladorVentanaPrincipal.mostrarEnTerminal("ERROR: la cantidad es inválida (ej: 23.45)");
            return;
        }
        //Delegamos la funcionalidad en el controlador de la aplicación
        controladorApp.addGasto(nombre, categoriaStr, cantidad, fecha);
        controladorVentanaPrincipal.mostrarEnTerminal("Gasto añadido: " + nombre + " en categoría " + categoriaStr + " por " + cantidad + " € el " + fecha);
        limpiarFormulario();
    }

    @FXML
    void cancelarAddGasto(ActionEvent event) {
    	limpiarFormulario();
    }
    
    private void limpiarFormulario() {
    	nombreGasto.clear();
    	categoriasCreadas.getSelectionModel().clearSelection();
    	cantidadGasto.clear();
    	fechaGasto.setValue(LocalDate.now());
    }
    
    @FXML
    void crearNuevaCategoria() {
    	TextInputDialog dialog = new TextInputDialog();	//mini ventanita
        dialog.setTitle("Nueva categoría");
        dialog.setHeaderText(null);
        dialog.setContentText("Nombre:");
        dialog.showAndWait().ifPresent(nombre -> {
            if (!nombre.isBlank()) {
            	//Evitamos distinguir entre mayúsculas, minúsculas y tildes
            	String categoriaStr = nombre.trim();
            	Categoria nueva = controladorApp.addCategoria(categoriaStr);
            	categoriasCreadas.getSelectionModel().select(nueva);
            	controladorVentanaPrincipal.mostrarEnTerminal("Categoría añadida: " + nombre);
            } else {
            	controladorVentanaPrincipal.mostrarEnTerminal("ERROR: La categoría no puede estar vacía");
            }
        });
    }
    
    public void inicializarCategorias() {
        categoriasCreadas.setItems(controladorApp.getCategorias());
        categoriasCreadas.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Categoria item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });
        categoriasCreadas.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Categoria item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });
    }

    @FXML
    void initialize() {
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'VentanaAñadirGasto.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'VentanaAñadirGasto.fxml'.";
        assert cantidadGasto != null : "fx:id=\"cantidadGasto\" was not injected: check your FXML file 'VentanaAñadirGasto.fxml'.";
        assert categoriasCreadas != null : "fx:id=\"categoriasCreadas\" was not injected: check your FXML file 'VentanaAñadirGasto.fxml'.";
        assert fechaGasto != null : "fx:id=\"fechaGasto\" was not injected: check your FXML file 'VentanaAñadirGasto.fxml'.";
        assert newCategoria != null : "fx:id=\"newCategoria\" was not injected: check your FXML file 'VentanaAñadirGasto.fxml'.";
        assert nombreGasto != null : "fx:id=\"nombreGasto\" was not injected: check your FXML file 'VentanaAñadirGasto.fxml'.";

        //Determinamos la fecha a la actual, por si el gasto se ha producido hoy
        fechaGasto.setValue(LocalDate.now());
    }
}
