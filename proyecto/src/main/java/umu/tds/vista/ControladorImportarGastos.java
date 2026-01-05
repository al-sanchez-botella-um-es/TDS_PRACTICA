package umu.tds.vista;

import javafx.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import umu.tds.controlador.Controlador;
import umu.tds.modelo.AdaptadorImportadorGastos;
import umu.tds.modelo.Categoria;
import umu.tds.modelo.FactoriaImportadorGastos;
import umu.tds.modelo.Gasto;

public class ControladorImportarGastos {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button importar;
    @FXML private Button upload;
    @FXML private TextField nombreArchivo;

    
    private ControladorVentanaPrincipal controladorVentanaPrincipal;
    private Controlador controladorApp;
    
    public void setControladorPrincipal(ControladorVentanaPrincipal controlador) {
        this.controladorVentanaPrincipal = controlador;
    }
    
    public void setControladorApp(Controlador controlador) {
        this.controladorApp = controlador;
    }
    

    @FXML
    private void seleccionarArchivo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo CSV");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File archivoSeleccionado = fileChooser.showOpenDialog(null);
        if (archivoSeleccionado != null) {
            // Name im Textfeld anzeigen
        	nombreArchivo.setText(archivoSeleccionado.getAbsolutePath());
            controladorVentanaPrincipal.mostrarEnTerminal(
                    "Archivo seleccionado: " + archivoSeleccionado.getName()
            );
        }
    }
    
    @FXML
    private void importarGastos(ActionEvent event) {
    	try {
    		File archivo = new File(nombreArchivo.getText());
    		AdaptadorImportadorGastos importador = FactoriaImportadorGastos.getImportador(archivo);
    		List<Gasto> gastos = importador.importar(archivo);
    		int count = 0;
    		for (Gasto g : gastos) {
    			Categoria cat = controladorApp.addCategoria(g.getCategoria().getNombre());
    			controladorApp.addGasto(g.getNombre(), cat.getNombre(), g.getCantidad(), g.getFecha());
    			count++;
    		}
    		controladorVentanaPrincipal.mostrarEnTerminal("Importación completada: " + count + " gastos añadidos.");
    	} catch (Exception e) {
    		controladorVentanaPrincipal.mostrarEnTerminal("Error al importar: " + e.getClass().getSimpleName() + " - " + e.getMessage());
    	}
    	nombreArchivo.clear();
    }
    
    @FXML
    void initialize() {
        assert importar != null : "fx:id=\"importar\" was not injected: check your FXML file 'importarGastos.fxml'.";
        assert upload != null : "fx:id=\"upload\" was not injected: check your FXML file 'importarGastos.fxml'.";

        nombreArchivo.setPromptText("Introduzca el archivo .csv");
    }
}
