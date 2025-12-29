package umu.tds.vista;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import umu.tds.controlador.Controlador;

public class ControladorRepresentacionGrafica {
	@FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private BarChart<String, Number> barras;
    @FXML private PieChart circular;
    private Controlador controladorApp;
    
    public void setControladorApp(Controlador controlador) {
    	this.controladorApp = controlador;
    	cargarGraficos();
    }

    private void cargarGraficos() {
        barras.getData().clear();
        circular.getData().clear();

        XYChart.Series<String, Number> serieBarras = new XYChart.Series<>();
        serieBarras.setName("Gastos por categoría");

        controladorApp.getCategorias().forEach(cat -> {
            double total = controladorApp.getGastos().stream()
                    .filter(g -> g.getCategoria() != null && g.getCategoria().equals(cat))
                    .mapToDouble(g -> g.getCantidad())
                    .sum();
            // Añadir al gráfico de barras
            serieBarras.getData().add(new XYChart.Data<>(cat.getNombre(), total));
            // Añadir al gráfico circular
            if (total > 0) {
                PieChart.Data slice = new PieChart.Data(cat.getNombre(), total);
                circular.getData().add(slice);
            }
        });
        barras.getData().add(serieBarras);
    }

	@FXML
    void initialize() {
        assert barras != null : "fx:id=\"barras\" was not injected: check your FXML file 'VentanaRepresentacionGrafica.fxml'.";
        assert circular != null : "fx:id=\"circular\" was not injected: check your FXML file 'VentanaRepresentacionGrafica.fxml'.";
    }
    
}