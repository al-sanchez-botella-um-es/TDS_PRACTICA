package umu.tds.vista;

///La terminal irá en una clase main aparte, con el mismo controlador y modelo pero diferente vista

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import umu.tds.controlador.Controlador;
/*import com.calendarfx.view.CalendarView;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Calendar.Style;*/
import umu.tds.modelo.Notificacion;

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
    @FXML private MenuItem menuCalendario;
    @FXML private MenuItem menuGraficos;
    @FXML private MenuItem menuHistorial;
    @FXML private Button modify;
    @FXML private VBox notif;
    @FXML private ListView<String> notificaciones;
    @FXML private Button remove;
    @FXML private TextArea terminal;
    private Controlador controladorApp;

    public void setControladorApp(Controlador controlador) {
        this.controladorApp = controlador;
        controladorApp.setVentanaPrincipal(this);	//necesario para que salten las notificaciones
    }
    
    public void mostrarEnTerminal(String texto) {
        terminal.appendText(texto + "\n");
    }
    
    //Correspondiente a las alertas -> notificaciones
    public void mostrarNotificacionEmergente(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("¡Alerta activada!");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public void actualizarNotificacionesDelDia() {
        notificaciones.getItems().clear();
        LocalDate hoy = LocalDate.now();
        for (Notificacion n : controladorApp.getNotificaciones()) {
            if (n.getFecha().equals(hoy)) {
                notificaciones.getItems().add(n.getMensaje());
            }
        }
    }


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
                ((ControladorAñadirGasto) controlador).inicializarCategorias();
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
            	((ControladorAlertas) controlador).cargarAlertas();
            }
            
            if (controlador instanceof ControladorCuentaCompartida) {
            	((ControladorCuentaCompartida) controlador).setControladorPrincipal(this);
            	((ControladorCuentaCompartida) controlador).setControladorApp(controladorApp);
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
    	abrirPestaña("Cuenta Compartida", "/umu/tds/VentanaCuentaCompartida.fxml");
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
    void mostrarCalendario(ActionEvent event) {
    	//Si ya existe la pestaña, seleccionarla
    	/*for (Tab tab : tabPane.getTabs()) {
    		if (tab.getText().equals("Calendario")) {
    			tabPane.getSelectionModel().select(tab);
    			return;
    		}
    	}
    	CalendarView calendarView = new CalendarView();
    	calendarView.setShowFullDay(true); // Vista de día completo
    	//Creamos uno para los gastos
    	Calendar gastosCalendar = new Calendar("Gastos");
    	gastosCalendar.setStyle(Style.STYLE1);
    	///Cargar gastos
    	controladorApp.getGastos().forEach(g -> {
    		Entry<String> entry = new Entry<>(g.getNombre() + " - " + g.getCantidad() + "€");
    		entry.setInterval(g.getFecha());
    		gastosCalendar.addEntry(entry);
    	});
    	CalendarSource source = new CalendarSource("Mis Calendarios");	//(contenedor de calendarios)
    	source.getCalendars().add(gastosCalendar);
    	calendarView.getCalendarSources().add(source);
    	calendarView.setRequestedTime(LocalTime.now());
    	// Crear pestaña
    	Tab tabCalendario = new Tab("Calendario");
    	tabCalendario.setClosable(true);
    	tabCalendario.setContent(calendarView);
    	// Añadir y seleccionar
    	tabPane.getTabs().add(tabCalendario);
    	tabPane.getSelectionModel().select(tabCalendario);
    	mostrarEnTerminal("Calendario abierto.");*/
    }

    @FXML
    void mostrarHistorialNotificaciones(ActionEvent event) {
	    /*for (Tab tab : tabPane.getTabs()) {
	        if (tab.getText().equals("Historial")) {
	            tabPane.getSelectionModel().select(tab);
	            return;
	        }
	    }

	    ListView<String> lista = new ListView<>();
	    controladorApp.getNotificaciones().forEach(n -> {
	        lista.getItems().add(n.getFecha() + " - " + n.getMensaje());
	    });
	    Tab tabHistorial = new Tab("Historial");
	    tabHistorial.setClosable(true);
	    tabHistorial.setContent(lista);
	    tabPane.getTabs().add(tabHistorial);
	    tabPane.getSelectionModel().select(tabHistorial);

	    mostrarEnTerminal("Historial de notificaciones abierto.");*/
    }

    @FXML
    void mostrarRepresentacionGrafica(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/umu/tds/VentanaRepresentacionGrafica.fxml"));
    		Parent root = loader.load();
    		ControladorRepresentacionGrafica controlador = loader.getController();
    		controlador.setControladorApp(controladorApp);
    		Tab tab = new Tab("Representación Gráfica");
    		tab.setClosable(true);
    		tab.setContent(root);
    		tabPane.getTabs().add(tab);
    		tabPane.getSelectionModel().select(tab);
    	} catch (IOException e) {
    		e.printStackTrace();
    		mostrarEnTerminal("Error al cargar la representación gráfica.");
    	}
    }

    @FXML
    void initialize() {
        assert account != null : "fx:id=\"account\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert alert != null : "fx:id=\"alert\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert botones != null : "fx:id=\"botones\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert filtro != null : "fx:id=\"filtro\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert menuCalendario != null : "fx:id=\"menuCalendario\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert menuGraficos != null : "fx:id=\"menuGraficos\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert menuHistorial != null : "fx:id=\"menuHistorial\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert modify != null : "fx:id=\"modify\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert notif != null : "fx:id=\"notif\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert remove != null : "fx:id=\"remove\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert tabPane != null : "fx:id=\"tabPane\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        assert terminal != null : "fx:id=\"terminal\" was not injected: check your FXML file 'VentanaPrincipalGastos.fxml'.";
        
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
