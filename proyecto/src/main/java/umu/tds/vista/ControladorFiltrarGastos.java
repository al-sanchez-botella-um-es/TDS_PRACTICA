package umu.tds.vista;

import umu.tds.controlador.Controlador;
import umu.tds.modelo.Categoria;
import umu.tds.modelo.Gasto;


import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
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
    @FXML private CheckBox mesesCheckBox;
    @FXML private ScrollPane mesesScrollPane;
    @FXML private VBox mesesVBox;
    
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
    
    private java.time.Month mesFromTexto(String texto) {
        return switch (texto) {
            case "Enero" -> java.time.Month.JANUARY;
            case "Febrero" -> java.time.Month.FEBRUARY;
            case "Marzo" -> java.time.Month.MARCH;
            case "Abril" -> java.time.Month.APRIL;
            case "Mayo" -> java.time.Month.MAY;
            case "Junio" -> java.time.Month.JUNE;
            case "Julio" -> java.time.Month.JULY;
            case "Agosto" -> java.time.Month.AUGUST;
            case "Septiembre" -> java.time.Month.SEPTEMBER;
            case "Octubre" -> java.time.Month.OCTOBER;
            case "Noviembre" -> java.time.Month.NOVEMBER;
            case "Diciembre" -> java.time.Month.DECEMBER;
            default -> throw new IllegalArgumentException("Mes desconocido: " + texto);
        };
    }
    
    private void aplicarFiltros() {
    	// Si NO hay ningún filtro activado -> restaurar lista original
    	if (!categoriaCheckBox.isSelected() && !mesesCheckBox.isSelected() && !fechaCheckBox.isSelected()) {
    		listaFiltrado.setItems(FXCollections.observableArrayList(controladorApp.getGastos()));
    		controladorVentanaPrincipal.mostrarEnTerminal("Sin filtros aplicados.");
    		return;
    	}
        List<Gasto> filtrados = controladorApp.getGastos();

        Set<Categoria> categoriasSeleccionadas = null;
        if (categoriaCheckBox.isSelected()) {
            List<String> nombresCategorias = categoriaVBox.getChildren().stream()
                    .filter(node -> node instanceof CheckBox cb && cb.isSelected())
                    .map(node -> ((CheckBox) node).getText())
                    .toList();
            if (!nombresCategorias.isEmpty()) {
                filtrados = controladorApp.filtrarCategoria(nombresCategorias);
                categoriasSeleccionadas = controladorApp.getCategorias().stream()
                        .filter(c -> nombresCategorias.contains(c.getNombre()))
                        .collect(Collectors.toSet());

                controladorVentanaPrincipal.mostrarEnTerminal("Filtrado de Gastos por Categoría.");
            }
        }
        Set<Month> mesesSeleccionados = null;
        if (mesesCheckBox.isSelected()) {
            List<Month> listaMeses = mesesVBox.getChildren().stream()
                    .filter(node -> node instanceof CheckBox cb && cb.isSelected())
                    .map(node -> mesFromTexto(((CheckBox) node).getText()))
                    .toList();
            if (!listaMeses.isEmpty()) {
                filtrados = controladorApp.filtrarFechaPorMeses(listaMeses);

                mesesSeleccionados = Set.copyOf(listaMeses);
                controladorVentanaPrincipal.mostrarEnTerminal("Filtrado de Gastos por Meses.");
            }
        }
        LocalDate desde = null;
        LocalDate hasta = null;
        if (fechaCheckBox.isSelected()) {
            desde = desdeDatePicker.getValue();
            hasta = hastaDatePicker.getValue();
            if (desde != null && hasta != null) {
                filtrados = controladorApp.filtrarFecha(desde, hasta);
                controladorVentanaPrincipal.mostrarEnTerminal("Filtrado de Gastos por Fecha.");
            } else {
                //Si falta alguna fecha, no se aplica el filtro
                desde = null;
                hasta = null;
            }
        }
        filtrados = controladorApp.filtrarGastos(
                mesesSeleccionados,
                desde,
                hasta,
                categoriasSeleccionadas
        );
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
    
    public void getMeses() {
        mesesVBox.getChildren().clear();
        String[] meses = {
            "Enero", "Febrero", "Marzo", "Abril",
            "Mayo", "Junio", "Julio", "Agosto",
            "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };
        for (String mes : meses) {
            CheckBox check = new CheckBox(mes);
            check.selectedProperty().addListener(
                (obs, oldVal, newVal) -> filtrarGastos.setSelected(false)
            );
            mesesVBox.getChildren().add(check);
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
        assert mesesCheckBox != null : "fx:id=\"mesesCheckBox\" was not injected: check your FXML file 'VentanaFiltrarGastos.fxml'.";
        assert mesesScrollPane != null : "fx:id=\"mesesScrollPane\" was not injected: check your FXML file 'VentanaFiltrarGastos.fxml'.";
        
      //Inicialmente, el ScrollPane de categorías y el GridPane de fecha están ocultos
        categoriaScrollPane.setVisible(false);
        categoriaScrollPane.setManaged(false);
        mesesScrollPane.setVisible(false);
        mesesScrollPane.setManaged(false);
        fechaGridPane.setVisible(false);
        fechaGridPane.setManaged(false);

        //Añadir un listener al CheckBox para mostrar/ocultar el ScrollPane / GridPane
        categoriaCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            categoriaScrollPane.setVisible(newVal);
            categoriaScrollPane.setManaged(newVal);
            if (newVal) {
                // Alle Kategorie-Checkboxen zurücksetzen
                categoriaVBox.getChildren().forEach(node -> {
                    if (node instanceof CheckBox cb) {
                        cb.setSelected(false);
                    }
                });
            }
        });

        mesesCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            mesesScrollPane.setVisible(newVal);
            mesesScrollPane.setManaged(newVal);
            if (newVal) {
                mesesVBox.getChildren().forEach(node -> {
                    if (node instanceof CheckBox cb) {
                        cb.setSelected(false);
                    }
                });
            }
        });

        fechaCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
			fechaGridPane.setVisible(newVal);
			fechaGridPane.setManaged(newVal);
		});
        
        //El botón debe reflejar visualmente que hay un filtro aplicado o no y cuál
        categoriaCheckBox.selectedProperty().addListener((o, ov, nv) -> filtrarGastos.setSelected(false));
        mesesCheckBox.selectedProperty().addListener((o, ov, nv) -> filtrarGastos.setSelected(false));
        fechaCheckBox.selectedProperty().addListener((o, ov, nv) -> filtrarGastos.setSelected(false));
    }
}