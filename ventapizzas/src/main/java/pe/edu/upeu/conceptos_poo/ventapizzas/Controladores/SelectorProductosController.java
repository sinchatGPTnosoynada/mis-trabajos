package pe.edu.upeu.conceptos_poo.ventapizzas.Controladores;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.Producto; // Importar el modelo real
import pe.edu.upeu.conceptos_poo.ventapizzas.service.ProductoIService;

import java.util.List;

@Controller
public class SelectorProductosController {

    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> colCodigo;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Long> colStock;


    @Autowired
    private ProductoIService productoService;


    private final ObservableList<Producto> lista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colCodigo.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getId_producto()))); // Usar ID
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colPrecio.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getPrecioU())); // Usar PrecioU
        colStock.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getStok())); // Usar stok (Long)


        cargarProductos();

        tablaProductos.setItems(lista);
    }

    private void cargarProductos() {
        try {
            List<Producto> productosDB = productoService.findAllProductos();
            lista.setAll(productosDB); // Carga la lista observable
        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo cargar la lista de productos: " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    private void seleccionarProducto() {
        Producto sel = tablaProductos.getSelectionModel().getSelectedItem(); // Obtiene el Producto seleccionado
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING, "Selecciona un producto.").showAndWait();
            return;
        }


        ProductoSeleccionadoHolder.ProductoItem itemSeleccionado = new ProductoSeleccionadoHolder.ProductoItem(
                String.valueOf(sel.getId_producto()),
                sel.getNombre(),
                sel.getPrecioU() != null ? sel.getPrecioU() : 0.0,
                sel.getStok() != null ? sel.getStok().intValue() : 0 // Convertir Long a int para el holder
        );


        ProductoSeleccionadoHolder.setProducto(itemSeleccionado);


        cerrarVentana();
    }


    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) tablaProductos.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }

}