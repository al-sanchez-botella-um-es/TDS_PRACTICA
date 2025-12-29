package umu.tds.vista;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import umu.tds.controlador.Controlador;
import umu.tds.modelo.Alerta;
import umu.tds.modelo.Categoria;

public class ControladorAlertas {
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private MenuButton categoriaBottom;
    @FXML private MenuButton frecuenciaBotton;
    @FXML private TextField cantidadLimite;
    @FXML private CheckMenuItem mensual;
    @FXML private CheckMenuItem semanal;
    @FXML private CheckMenuItem anual;
    @FXML private Button cancelAlert;
    @FXML private Button newAlert;
    @FXML private ListView<CheckBox> alertas;
    @FXML private CheckBox seleccionarTodoCheckBox;
    @FXML private Button borrarButton;
    private ControladorVentanaPrincipal controladorVentanaPrincipal;
    private Controlador controladorApp;

    public void setControladorPrincipal(ControladorVentanaPrincipal controlador) {
        this.controladorVentanaPrincipal = controlador;
    }
    
    public void setControladorApp(Controlador controlador) {
        this.controladorApp = controlador;
    }
    
    @FXML
    void addAlerta(ActionEvent event) {
    	String frecuenciaStr = frecuenciaBotton.getText();
        String categoriaStr = categoriaBottom.getText();
        String limiteStr = cantidadLimite.getText();
        //Validaciones
        if (frecuenciaStr == null || frecuenciaStr.isBlank()) {
            controladorVentanaPrincipal.mostrarEnTerminal("ERROR: selecciona una frecuencia.");
            return;
        }
        if (categoriaStr == null || categoriaStr.isBlank() || categoriaStr.equals("Selecciona una categoría.")) {
            controladorVentanaPrincipal.mostrarEnTerminal("ERROR: selecciona una categoría.");
            return;
        }
        if (limiteStr == null || limiteStr.isBlank()) {
            controladorVentanaPrincipal.mostrarEnTerminal("ERROR: introduce un límite económico.");
            return;
        }
        double limite = Double.parseDouble(limiteStr);
        Alerta alerta = controladorApp.configurarAlerta(frecuenciaStr, categoriaStr, limite);
        CheckBox cb = new CheckBox(alerta.toString());
        cb.setUserData(alerta);
        alertas.getItems().add(cb);
		controladorVentanaPrincipal.mostrarEnTerminal("Alerta configurada : " + alerta);
		limpiarFormulario();
    }
    
    public void limpiarFormulario() {
    	semanal.setSelected(false);
		mensual.setSelected(false);
		anual.setSelected(false);
		frecuenciaBotton.setText("");
		for (Object item : categoriaBottom.getItems()) {
			if (item instanceof CheckMenuItem Citem) {
				Citem.setSelected(false);
			}
		}
		cantidadLimite.clear();
		// Actualizar textos y reglas
		categoriaBottom.setText("");
		updateCategoriaText();
    }

    //Cancelar regla y/o limpiar campos
    @FXML
    void cancelarConfiguracion(ActionEvent event) {
    	limpiarFormulario();
    }
    
    //Borrar alertas seleccionadas -> hay que añadir persistencia
    @FXML
    private void borrarAlertas() {
        alertas.getItems().removeIf(cb -> {
            if (cb.isSelected()) {
                Alerta alerta = (Alerta) cb.getUserData();
                if (alerta != null) {
                    controladorApp.removeAlerta(alerta);
                    controladorVentanaPrincipal.mostrarEnTerminal("Alerta desactivada : " + alerta);
                }
                return true;
            }
            return false;
        });
    }
        
    //Refleja las opciones marcadas por el usuario
    private void updateCategoriaText() {
    	CheckMenuItem seleccionada = null;
        for (Object item : categoriaBottom.getItems()) {
            if (item instanceof CheckMenuItem item2 && item2.isSelected()) {
                seleccionada = item2;
                break;
            }
        }
        if (seleccionada != null) {
        	//Desmarcar las demás opciones
        	for (Object item : categoriaBottom.getItems()) {
                if (item instanceof CheckMenuItem otro && otro != seleccionada) {
                    otro.setSelected(false);
                }
            }
        	categoriaBottom.setText(seleccionada.getText());
        }
    }

    
    public void cargarCategorias() {
        categoriaBottom.getItems().clear();
        if (controladorApp != null) {
            for (Categoria categoria : controladorApp.getCategorias()) {
                CheckMenuItem item = new CheckMenuItem(categoria.getNombre());
                item.setOnAction(e -> {
                    // Desmarcar todas las demás
                    for (Object other : categoriaBottom.getItems()) {
                        if (other instanceof CheckMenuItem o && o != item) {
                            o.setSelected(false);
                        }
                    }
                    // Actualizar texto del botón
                    categoriaBottom.setText(item.getText());
                });
                categoriaBottom.getItems().add(item);
            }
        }
        // Inicializar el texto del botón
        updateCategoriaText();
    }
    
    public void cargarAlertas() {
    	alertas.getItems().clear();
    	for(Alerta alerta : controladorApp.getAlertas()) {
    		CheckBox cb = new CheckBox(alerta.toString());
    		cb.setUserData(alerta);
    		alertas.getItems().add(cb);
    	}
    }

    @FXML
    void initialize() {
        assert anual != null : "fx:id=\"anual\" was not injected: check your FXML file 'VentanaAlertas.fxml'.";
        assert categoriaBottom != null : "fx:id=\"categoriaBottom\" was not injected: check your FXML file 'VentanaAlertas.fxml'.";
        assert frecuenciaBotton != null : "fx:id=\"frecuenciaBotton\" was not injected: check your FXML file 'VentanaAlertas.fxml'.";
        assert mensual != null : "fx:id=\"mensual\" was not injected: check your FXML file 'VentanaAlertas.fxml'.";
        assert semanal != null : "fx:id=\"semanal\" was not injected: check your FXML file 'VentanaAlertas.fxml'.";
        
        semanal.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                frecuenciaBotton.setText("semanal");
                mensual.setSelected(false);
                anual.setSelected(false);
            }
        });
        mensual.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                frecuenciaBotton.setText("mensual");
                semanal.setSelected(false);
                anual.setSelected(false);
            }
        });
        anual.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                frecuenciaBotton.setText("anual");
                semanal.setSelected(false);
                mensual.setSelected(false);
            }
        });
        
        seleccionarTodoCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            for (CheckBox regla : alertas.getItems()) {
                regla.setSelected(newVal);
            }
        });
    }
}