package umu.tds.vista;

import javafx.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import umu.tds.controlador.Controlador;

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
            nombreArchivo.setText(archivoSeleccionado.getName());
            controladorVentanaPrincipal.mostrarEnTerminal(
                    "Archivo seleccionado: " + archivoSeleccionado.getName()
            );
        }
    }
    
    @FXML
    private void importarGastos(ActionEvent event) {
    }
    

/*
    private void importarCSV(File archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { 
                    primeraLinea = false; 
                    continue; // Kopfzeile überspringen
                }
                String[] datos = linea.split(",");
                if (datos.length != 4) continue; // Fehlerhafte Zeile überspringen

                String nombre = datos[0].trim();
                String categoriaStr = datos[1].trim();
                double cantidad = Double.parseDouble(datos[2].trim());
                LocalDate fecha = LocalDate.parse(datos[3].trim());

                // Hier die Gasto hinzufügen
                controladorApp.addGasto(nombre, categoriaStr, cantidad, fecha);
            }
            controladorVentanaPrincipal.mostrarEnTerminal("CSV importiert: " + archivo.getName());
        } catch (Exception e) {
            e.printStackTrace();
            controladorVentanaPrincipal.mostrarEnTerminal("Fehler beim Import der CSV: " + e.getMessage());
        }
    }
*/
    
    
    @FXML
    void initialize() {
        assert importar != null : "fx:id=\"importar\" was not injected: check your FXML file 'importarGastos.fxml'.";
        assert upload != null : "fx:id=\"upload\" was not injected: check your FXML file 'importarGastos.fxml'.";

    }

}
