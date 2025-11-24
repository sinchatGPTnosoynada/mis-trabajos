package pe.edu.upeu.conceptos_poo.ventapizzas.Controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.conceptos_poo.ventapizzas.components.JasperViewerFX;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.Venta;
import pe.edu.upeu.conceptos_poo.ventapizzas.service.VentaService;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReporteVentasController {

    @FXML private DatePicker dpFechaInicio;
    @FXML private DatePicker dpFechaFin;

    @Autowired
    private VentaService ventaService;

    @FXML
    public void initialize() {
        dpFechaInicio.setValue(LocalDate.now());
        dpFechaFin.setValue(LocalDate.now());
    }

    @FXML
    private void generarReporte() {
        if (dpFechaInicio.getValue() == null || dpFechaFin.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar ambas fechas.");
            return;
        }

        if (dpFechaInicio.getValue().isAfter(dpFechaFin.getValue())) {
            mostrarAlerta("Error", "La fecha de inicio no puede ser mayor a la fecha fin.");
            return;
        }

        try {
            LocalDateTime fechaInicio = dpFechaInicio.getValue().atStartOfDay();
            LocalDateTime fechaFin = dpFechaFin.getValue().atTime(LocalTime.MAX);

            List<Venta> listaVentas = ventaService.buscarPorRangoFecha(fechaInicio, fechaFin);

            if (listaVentas.isEmpty()) {
                mostrarAlerta("Información", "No se encontraron ventas en el rango seleccionado.");
                return;
            }

            // --- CORRECCIÓN AQUÍ: Cambiado "/reportes/" por "/jasper/" ---
            InputStream reportStream = getClass().getResourceAsStream("/jasper/ventas_fechas.jrxml");

            if (reportStream == null) {
                mostrarAlerta("Error", "No se encontró el archivo del reporte (/jasper/ventas_fechas.jrxml).");
                return;
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("fechaInicio", dpFechaInicio.getValue().toString());
            parametros.put("fechaFin", dpFechaFin.getValue().toString());

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaVentas);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

            JasperViewerFX viewer = new JasperViewerFX();
            viewer.viewReport("Reporte de Ventas", jasperPrint);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error Crítico", "Error al generar el reporte: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}