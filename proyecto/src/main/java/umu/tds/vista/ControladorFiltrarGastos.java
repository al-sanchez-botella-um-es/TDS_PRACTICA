package umu.tds.vista;

import umu.tds.controlador.Controlador;
import umu.tds.modelo.Categoria;
import umu.tds.modelo.Gasto;


import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
	
public class ControladorFiltrarGastos {
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private ScrollPane categoriaScrollPane;
    @FXML private ToggleButton filtrarGastos;
    @FXML private VBox botones;
    @FXML private CheckBox categoriaCheckBox;
    @FXML private VBox categoriaVBox;
    @FXML private CheckBox fechaCheckBox;
    @FXML private GridPane fechaGridPane;
    @FXML private Text filtrar;
    @FXML private DatePicker desdeDatePicker;
    @FXML private DatePicker hastaDatePicker;
    @FXML private ListView<Gasto> listaFiltrado;
    private ControladorVentanaPrincipal controladorVentanaPrincipal;
    private Controlador controladorApp;
    private final ObservableList<Gasto> todosGastos = FXCollections.observableArrayList();
    
    public void setControladorApp(Controlador controlador) {
        this.controladorApp = controlador;
        todosGastos.setAll(controladorApp.getGastos());
        listaFiltrado.setItems(FXCollections.observableArrayList(todosGastos));
    }
    
    public void setControladorPrincipal(ControladorVentanaPrincipal controlador) {
        this.controladorVentanaPrincipal = controlador;
    }
    
    private void aplicarFiltros() {
    	List<Gasto> filtrados = controladorApp.getGastos();
    	//Delegamos la funcionalidad en el controlador
        // Filter Categoria
        if (categoriaCheckBox.isSelected()) {
            List<String> categoriasSeleccionadas = categoriaVBox.getChildren().stream()
                    .filter(node -> node instanceof CheckBox cb && cb.isSelected())
                    .map(node -> ((CheckBox) node).getText())
                    .toList();

            if (!categoriasSeleccionadas.isEmpty()) {
            	filtrados = controladorApp.filtrarCategoria(categoriasSeleccionadas);
            	controladorVentanaPrincipal.mostrarEnTerminal("Filtrado de Gastos por Categoria.");
            }
        }

        // Filter Fecha
        if (fechaCheckBox.isSelected()) {
            LocalDate desde = desdeDatePicker.getValue();
            LocalDate hasta = hastaDatePicker.getValue();

            if (desde != null && hasta != null) {
                filtrados = controladorApp.filtrarFecha(desde, hasta);
                controladorVentanaPrincipal.mostrarEnTerminal("Filtrado de Gastos por Fecha.");
            }
        }
        listaFiltrado.setItems(FXCollections.observableArrayList(filtrados));
    }
        
    @FXML
    private void aplicarFiltradoDeGastos(ActionEvent event) {
        aplicarFiltros();
    }
    
    public void cargarCategorias() {
        categoriaVBox.getChildren().clear();
        //Si se activa la opción de Categoría, se mostrarán las categorías registradas en el sistema
        for (Categoria categoria : controladorApp.getCategorias()) {
            CheckBox check = new CheckBox(categoria.getNombre());
            check.selectedProperty().addListener((obs, oldVal, newVal) -> filtrarGastos.setSelected(false));
            categoriaVBox.getChildren().add(check);
        }
    }
    
    @FXML
    void initialize() {
        assert categoriaScrollPane != null : "fx:id=\"CategoriaScrollPane\" was not injected: check your FXML file 'VentanaFiltrarGastos.fxml'.";
        assert filtrarGastos != null : "fx:id=\"applicarToggleButton\" was not injected: check your FXML file 'VentanaFiltrarGastos.fxml'.";
        assert botones != null : "fx:id=\"botones\" was not injected: check your FXML file 'VentanaFiltrarGastos.fxml'.";
        assert categoriaCheckBox != null : "fx:id=\"categoriaCheckBox\" was not injected: check your FXML file 'VentanaFiltrarGastos.fxml'.";
        assert fechaCheckBox != null : "fx:id=\"fechaCeckBox\" was not injected: check your FXML file 'VentanaFiltrarGastos.fxml'.";
        assert filtrar != null : "fx:id=\"filtrar\" was not injected: check your FXML file 'VentanaFiltrarGastos.fxml'.";
        assert fechaGridPane != null : "fx:id=\"fechaGridPane\" was not injected: check your FXML file 'VentanaFiltrarGastos.fxml'.";
        
        //Inicialmente, el ScrollPane de categorías y el GridPane de fecha están ocultos
        categoriaScrollPane.setVisible(false);
        categoriaScrollPane.setManaged(false);
        fechaGridPane.setVisible(false);
        fechaGridPane.setManaged(false);

        //Añadir un listener al CheckBox para mostrar/ocultar el ScrollPane / GridPane
        categoriaCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            categoriaScrollPane.setVisible(newVal);
            categoriaScrollPane.setManaged(newVal);
        });
        fechaCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
			fechaGridPane.setVisible(newVal);
			fechaGridPane.setManaged(newVal);
		});
        
        //El botón debe reflejar visualmente que hay un filtro aplicado o no y cuál
        categoriaCheckBox.selectedProperty().addListener((o, ov, nv) -> filtrarGastos.setSelected(false));
        fechaCheckBox.selectedProperty().addListener((o, ov, nv) -> filtrarGastos.setSelected(false));
    }
}