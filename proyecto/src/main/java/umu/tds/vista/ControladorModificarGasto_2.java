package umu.tds.vista;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import umu.tds.controlador.Controlador;
import umu.tds.modelo.Categoria;
import umu.tds.modelo.Gasto;
import umu.tds.repository.impl.RepositorioCategoriaJSON;
import umu.tds.repository.impl.RepositorioGastoJSON;

public class ControladorModificarGasto_2{
	private Gasto gastoAEditar;
    // Instancia del repositorio para guardar los cambios
	private final RepositorioGastoJSON repositorioGastos = RepositorioGastoJSON.getInstance();
    
    // 2. Instancia del Repositorio de Categorías (Singleton)
    private final RepositorioCategoriaJSON repositorioCategorias = RepositorioCategoriaJSON.getInstance();
    @FXML private ResourceBundle resources;

    @FXML private URL location;

    @FXML private Button modificar_2;

    @FXML private Button cancel;

    @FXML private TextField cantidadGasto;

    @FXML private ComboBox<Categoria> categoriasCreadas;

    @FXML private DatePicker fechaGasto;
    @FXML private Button newCategoria;
    @FXML private TextArea terminal;
    @FXML private TextField nombreGasto;
    //PARA CAMBIAR DE VENTANA Y PODER MODIFICARLO
    private ControladorModificarGasto controladorModif;
    private ControladorVentanaPrincipal controladorVentanaPrincipal;
    private Controlador controladorApp;
    @FXML private ListView<Gasto> listaGastos;
    private Categoria categoriaGasto;
    private DatePicker fecha;
    
    public void setControladorApp(Controlador controlador) {
        this.controladorApp = controlador;
    }
    
    public void setControladorModif(ControladorModificarGasto controlador) {//ESTO DESDE MODIF -> MODIF2
        this.controladorModif = controlador;
    }
    
    //PARA INICIALIZAR
    public void initData(Gasto gasto) {
        this.gastoAEditar = gasto;
        
        // Cargar los datos del objeto Gasto en los elementos FXML
        nombreGasto.setText(gasto.getNombre());
        cantidadGasto.setText(String.valueOf(gasto.getCantidad()));
        fechaGasto.setValue(gasto.getFecha());
     // 2. Lógica del ComboBox (LA PARTE CLAVE)
        categoriasCreadas.getItems().clear(); // Limpiamos lo que haya

        // CARGAMOS TODAS LAS CATEGORÍAS DEL REPOSITORIO
        ObservableList<Categoria> todas = repositorioCategorias.findAll();
        categoriasCreadas.getItems().addAll(todas); 

        // Seleccionamos la actual si existe
        if (gasto.getCategoria() != null) {
            categoriasCreadas.getSelectionModel().select(gasto.getCategoria());
        }
    }
        
    @FXML
    void modificarGasto_2(ActionEvent event) {
    	if (gastoAEditar != null) {
            try {
                // Leer los nuevos valores de los campos
                String nuevoNombre = nombreGasto.getText();
                double nuevaCantidad = Double.parseDouble(cantidadGasto.getText());
                LocalDate nuevaFecha = fechaGasto.getValue();
                Categoria nuevaCategoria = categoriasCreadas.getSelectionModel().getSelectedItem();
                // Puedes validar la entrada aquí (p. ej., que no esté vacío)

                // Actualizar el objeto Gasto con los nuevos valores
                gastoAEditar.setNombre(nuevoNombre);
                gastoAEditar.setCantidad(nuevaCantidad);
                gastoAEditar.setFecha(nuevaFecha);
                if (nuevaCategoria != null) {
                    gastoAEditar.setCategoria(nuevaCategoria);
                }
                // Persistir el gasto actualizado en el repositorio
                repositorioGastos.save(gastoAEditar); 
                
                // Cerrar la ventana de modificación
                cancelarModifGasto_2(event); // Reutilizamos el método para cerrar la ventana
            } catch (NumberFormatException e) {
                // Manejar error si la cantidad no es un número válido
                System.err.println("Error: La cantidad debe ser un número válido.");
                // Deberías mostrar un Alert al usuario aquí
            }
        }
    }
    

    public void mostrarEnTerminal(String texto) {
        terminal.appendText(texto + "\n");
    }
    
    private void abrirPestaña(String rutaFXML) {
    	try {
    		//Cargamos la pestaña correspondiente
    		FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent contenido = loader.load();

            //Recuperar el controlador de la pestaña
            Object controlador = loader.getController();
            if (controlador instanceof ControladorModificarGasto) {
                ((ControladorModificarGasto) controlador).setControladorModif_2(this);
            }
    	}
    	catch (IOException e) {
            e.printStackTrace();
            mostrarEnTerminal("Error al cargar la ventana");
        }
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
                // 1. Crear el objeto
                Categoria nuevaCategoria = new Categoria(nombre);
                
                // 2. Guardar en el disco (JSON)
                // Esto asegura que salga la próxima vez que abras la app
                repositorioCategorias.save(nuevaCategoria);
                
                // 3. --- EL PASO QUE TE FALTA ---
                // Añadirlo VISUALMENTE al ComboBox ahora mismo.
                // Si no haces esto, la lista visual no se entera del cambio.
                if (!categoriasCreadas.getItems().contains(nuevaCategoria)) {
                    categoriasCreadas.getItems().add(nuevaCategoria);
                }
                
                // 4. Seleccionarla automáticamente para comodidad del usuario
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

