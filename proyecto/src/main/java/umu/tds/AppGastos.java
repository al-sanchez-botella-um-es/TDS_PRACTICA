package umu.tds;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import umu.tds.controlador.Controlador;
import umu.tds.vista.ControladorVentanaPrincipal;

///Sólo hay una 'Stage' (ventana principal)
///Y la 'Scene' (escena) define el aspecto de las diferentes ventanas

public class AppGastos extends Application {
	@Override
	public void start(Stage ventanaPrincipal) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaPrincipalGastos.fxml"));
		Parent contenedor = loader.load();
		ControladorVentanaPrincipal controladorVentana = loader.getController();
		Controlador controladorApp = new Controlador();
		controladorVentana.setControladorApp(controladorApp);
		
        Scene escena = new Scene(contenedor, 1000, 600);	//tamaño de la mini ventana al ejecutar	
        ventanaPrincipal.setTitle("Aplicación: Gestión de Gastos");
        ventanaPrincipal.setScene(escena);
        //para que el usuario no amplíe la aplicación y siempre tenga el mismo tamaño
        ventanaPrincipal.setResizable(false);
        ventanaPrincipal.show();
	}
	
	public static void main( String[] args ) {
        launch(args);
    }
}
