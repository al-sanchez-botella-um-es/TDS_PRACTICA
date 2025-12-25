package umu.tds.vista;

///La terminal irá en una clase main aparte, con el mismo controlador y modelo pero diferente vista

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
import umu.tds.controlador.Controlador;

public class ControladorVentanaPrincipal {
	@FXML private Tab ventanaPrincipal; //pestaña inicial
	@FXML private MenuItem salir;
	@FXML private TabPane tabPane;	//en él se mostrarán las diferentes pestañas al pulsar los botones
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button account;
    @FXML private Button add;
    @FXML private Button alert;
    @FXML private VBox botones;
    @FXML private Button filtro;
    @FXML private Button modify;
    @FXML private VBox notif;
    @FXML private Button remove;
    @FXML private TextArea terminal;
    private Controlador controladorApp;

    public void setControladorApp(Controlador controlador) {
        this.controladorApp = controlador;
    }
    
    public void mostrarEnTerminal(String texto) {
        terminal.appendText(texto + "\n");
    }
    
    ///Correspondientes a las alertas
    /*private void mostrarNotificacion(String mensaje) {
        Text texto = new Text(mensaje);
        texto.setStyle("-fx-fill: #333; -fx-font-size: 13px;");
        notif.getChildren().add(texto);
    }*/

    private void abrirPestaña(String titulo, String rutaFXML) {
    	try {
    		// Si ya existe una pestaña con ese título, seleccionarla y salir
            for (Tab tab : tabPane.getTabs()) {
                if (tab.getText().equals(titulo)) {
                    tabPane.getSelectionModel().select(tab);
                    return;
                }
            }
    		//Cargamos la pestaña correspondiente
    		FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent contenido = loader.load();

            //Recuperar el controlador de la pestaña
            Object controlador = loader.getController();
            if (controlador instanceof ControladorAñadirGasto) {
                ((ControladorAñadirGasto) controlador).setControladorPrincipal(this);
                ((ControladorAñadirGasto) controlador).setControladorApp(controladorApp);
                ((ControladorAñadirGasto) controlador).cargarCategorias();
            }
            
            if (controlador instanceof ControladorEliminarGasto) {
                ((ControladorEliminarGasto) controlador).setControladorPrincipal(this);
                ((ControladorEliminarGasto) controlador).setControladorApp(controladorApp);
                ((ControladorEliminarGasto) controlador).cargarGastos();
            }
            
            if (controlador instanceof ControladorModificarGasto) {
                ((ControladorModificarGasto) controlador).setControladorPrincipal(this);
                ((ControladorModificarGasto) controlador).setControladorApp(controladorApp);
                ((ControladorModificarGasto) controlador).cargarGastos();
            }
            
            if (controlador instanceof ControladorFiltrarGastos) {
            	((ControladorFiltrarGastos) controlador).setControladorPrincipal(this);
            	((ControladorFiltrarGastos) controlador).setControladorApp(controladorApp);
            	((ControladorFiltrarGastos) controlador).cargarCategorias();
            }
            
            if (controlador instanceof ControladorAlertas) {
            	((ControladorAlertas) controlador).setControladorPrincipal(this);
            	((ControladorAlertas) controlador).setControladorApp(controladorApp);
            	((ControladorAlertas) controlador).cargarCategorias();
            }
            
            Tab nuevaTab = new Tab(titulo);
            nuevaTab.setClosable(true);	
            nuevaTab.setContent(contenido);
            tabPane.getTabs().add(nuevaTab);
            tabPane.getSelectionModel().select(nuevaTab);
    	}
    	catch (IOException e) {
            e.printStackTrace();
            mostrarEnTerminal("Error al cargar: " + titulo);
        }
    }
    
    @FXML
    void addGasto(ActionEvent event) {
    	abrirPestaña("Añadir Gasto", "/umu/tds/VentanaAñadirGasto.fxml");
    }
    
    @FXML
    void modifyGasto(ActionEvent event) {
    	abrirPestaña("Modificar Gasto", "/umu/tds/VentanaModificarGasto.fxml");
    }

    @FXML
    void removeGasto(ActionEvent event) {
    	abrirPestaña("Eliminar Gasto", "/umu/tds/VentanaEliminarGasto.fxml");
    }

    @FXML
    void configurarAlertas(ActionEvent event) {
    	abrirPestaña("Configurar Alerta", "/umu/tds/VentanaAlertas.fxml");
    }

    @FXML
    void crearCuentaCompartida(ActionEvent event) {
    	//abrirPestaña("Crear Cuenta Compartida", "/umu/tds/VentanaCuentaCompartida.fxml");
    }

    @FXML
    void filtro(ActionEvent event) {
    	abrirPestaña("Filtrar Gastos", "/umu/tds/VentanaFiltrarGastos.fxml");
    }
    
    @FXML
    void volverAlInicio(ActionEvent event) {
        tabPane.getTabs().clear();
        mostrarEnTerminal("Has vuelto a la ventana principal.");
    }
    
    @FXML
    void salirDeLaAplicacion(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void initialize() {
        assert account != null : "fx:id=\"account\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert alert != null : "fx:id=\"alert\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert botones != null : "fx:id=\"botones\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert filtro != null : "fx:id=\"filtro\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert modify != null : "fx:id=\"modify\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert notif != null : "fx:id=\"notif\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert remove != null : "fx:id=\"remove\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        
        //Ocultar barra de pestañas
        tabPane.setTabMaxHeight(0);
        tabPane.setTabMinHeight(0);
        
        //Pestañas
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
        	if (newTab != null) {
        		mostrarEnTerminal("Has cambiado a la pestaña: " + newTab.getText());
        	}
        });
    }
}
