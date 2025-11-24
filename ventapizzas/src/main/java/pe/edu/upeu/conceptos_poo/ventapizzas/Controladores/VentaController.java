package pe.edu.upeu.conceptos_poo.ventapizzas.Controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode; // Importado para detectar la tecla Enter

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.context.annotation.Scope;

// Modelos
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.Producto;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.Usuario;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.Venta;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.DetalleVenta;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.Cliente; // Importado modelo Cliente
import pe.edu.upeu.conceptos_poo.ventapizzas.enums.TipoDocumento; // Importar Enum
import pe.edu.upeu.conceptos_poo.ventapizzas.dto.ModeloDataAutocomplet; // Importar DTO para el autocompletado

// Servicios
import pe.edu.upeu.conceptos_poo.ventapizzas.service.ProductoIService;
import pe.edu.upeu.conceptos_poo.ventapizzas.service.UsuarioService;
import pe.edu.upeu.conceptos_poo.ventapizzas.service.VentaService;
import pe.edu.upeu.conceptos_poo.ventapizzas.service.ClienteService; // Importado servicio Cliente

// Componentes
import pe.edu.upeu.conceptos_poo.ventapizzas.components.AutoCompleteTextField; // Importar tu componente de autocompletado

import pe.edu.upeu.conceptos_poo.ventapizzas.dto.SessionManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;


@Controller
@Scope("prototype") // Cada vez que se abra la pestaña de venta, será una nueva instancia
public class VentaController {

    // --- Servicios Inyectados ---
    @Autowired private VentaService ventaService;
    @Autowired private ProductoIService productoService;
    @Autowired private UsuarioService usuarioService;
    @Autowired private ConfigurableApplicationContext applicationContext;
    @Autowired private ClienteService clienteService; // Servicio de clientes habilitado

    // ===== Encabezado (Buscar) =====
    @FXML private TextField txtFiltroDato; // Aquí aplicaremos el autocompletado
    @FXML private Label idPrueba; // opcional

    // ===== Datos del cliente =====
    @FXML private TextField txtClienteDni; // Ahora va primero
    @FXML private TextField txtClienteNombre;
    @FXML private TextField txtClienteApellido;
    @FXML private ComboBox<TipoDocumento> cbTipoDocumento; // Nuevo campo para crear cliente

    // ===== Pago =====
    @FXML private ComboBox<String> cbMetodoPago;
    @FXML private TextField txtNumTarjeta;
    @FXML private TextField txtCvv;

    // ===== Línea de producto =====
    @FXML private TextField txtNombreProducto;
    @FXML private TextField txtPrecioUnitario;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtTotal; // Total general de la tabla
    @FXML private Label lbnMsg;

    // ===== Tabla =====
    @FXML private TableView<VentaItem> tablaVenta;
    @FXML private TableColumn<VentaItem, String>  colProducto; // Muestra el Nombre del producto
    @FXML private TableColumn<VentaItem, Double>  colPrecio;
    @FXML private TableColumn<VentaItem, Integer> colCantidad;
    @FXML private TableColumn<VentaItem, Double>  colSubtotal;

    // Lista observable para la tabla de items de venta
    private final ObservableList<VentaItem> listaVenta = FXCollections.observableArrayList();

    // Variable para guardar temporalmente el ID del producto seleccionado
    private Long idProductoSeleccionado = null;

    // Variable para los datos del autocompletado
    private SortedSet<ModeloDataAutocomplet> entries;

    @FXML
    public void initialize() {
        // Configuración de la Tabla
        colProducto.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombreProducto()));
        colPrecio.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getPrecioUnitario()).asObject());
        colCantidad.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getCantidad()).asObject());
        colSubtotal.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getSubtotal()).asObject());
        tablaVenta.setItems(listaVenta);

        // Configuración ComboBox Método de Pago
        if (cbMetodoPago != null) {
            cbMetodoPago.setItems(FXCollections.observableArrayList("Efectivo", "Débito", "Crédito"));
            cbMetodoPago.getSelectionModel().selectFirst(); // Selecciona "Efectivo" por defecto
            cbMetodoPago.valueProperty().addListener((obs, oldVal, newVal) -> toggleCamposTarjeta());
            toggleCamposTarjeta(); // Ejecuta una vez al inicio para deshabilitar campos de tarjeta
        }

        // Configuración ComboBox Tipo Documento
        if (cbTipoDocumento != null) {
            cbTipoDocumento.setItems(FXCollections.observableArrayList(TipoDocumento.values()));
            cbTipoDocumento.getSelectionModel().select(TipoDocumento.DNI); // Por defecto DNI
        }

        // Listener para buscar cliente al presionar ENTER en el campo DNI
        if (txtClienteDni != null) {
            txtClienteDni.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    buscarCliente();
                }
            });
        }

        // --- CONFIGURACIÓN DEL AUTOCOMPLETADO PARA PRODUCTOS ---
        // Esto hace que aparezca la lista mientras escribes
        if (txtFiltroDato != null) {
            // 1. Inicializamos la lista ordenada
            entries = new TreeSet<>(Comparator.comparing(ModeloDataAutocomplet::toString));

            // 2. Cargamos los productos desde la BD usando el servicio
            // Asumiendo que el servicio tiene este método que devuelve List<ModeloDataAutocomplet>
            try {
                List<ModeloDataAutocomplet> productosData = productoService.listAutoCompletProducto();
                entries.addAll(productosData);

                // 3. Vinculamos el TextField con el componente de autocompletado
                new AutoCompleteTextField<>(entries, txtFiltroDato);
            } catch (Exception e) {
                System.out.println("Error cargando autocompletado: " + e.getMessage());
            }
        }
        // ------------------------------------------------------

        // Listener para calcular subtotal al cambiar cantidad o precio (si se editan)
        txtCantidad.textProperty().addListener((obs, ov, nv) -> calcularSubtotalItem());
        txtPrecioUnitario.textProperty().addListener((obs, ov, nv) -> calcularSubtotalItem());

        actualizarTotalGeneral(); // Calcula el total inicial (0.00)
    }

    // ===== Botones del FXML =====

    @FXML
    private void buscarProducto() {
        String q = nvl(txtFiltroDato != null ? txtFiltroDato.getText() : "");
        if (q.isBlank()) { info("Escribe algo para buscar (nombre o ID)."); return; }

        try {
            Optional<Producto> productoOpt = Optional.empty();

            // 1. Intentar identificar si se seleccionó un item del autocompletado
            // El formato del toString() es: "Nombre ID Data"
            if (entries != null) {
                for (ModeloDataAutocomplet entry : entries) {
                    // Si lo que hay en el texto coincide exactamente con una entrada del autocompletado
                    if (entry.toString().equals(q)) {
                        // Extraemos el ID (que está en getIdx())
                        try {
                            Long idReal = Long.parseLong(entry.getIdx());
                            Producto p = productoService.findProductoById(idReal);
                            if (p != null) productoOpt = Optional.of(p);
                        } catch (Exception ignore) {}
                        break;
                    }
                }
            }

            // 2. Si no se encontró por selección exacta, intentar buscar por ID directo o Nombre
            if (productoOpt.isEmpty()) {
                try {
                    Long idBusqueda = Long.parseLong(q);
                    Producto p = productoService.findProductoById(idBusqueda);
                    if (p != null) productoOpt = Optional.of(p);
                } catch (NumberFormatException nfe) {
                    // Si no es un número, busca por nombre (primera coincidencia en BD)
                    List<Producto> encontrados = productoService.findAllProductos().stream()
                            .filter(p -> p.getNombre().toLowerCase().contains(q.toLowerCase()))
                            .collect(Collectors.toList());
                    if (!encontrados.isEmpty()) productoOpt = Optional.of(encontrados.get(0));
                }
            }

            if (productoOpt.isPresent()) {
                Producto p = productoOpt.get();
                idProductoSeleccionado = p.getId_producto(); // Guarda el ID
                txtNombreProducto.setText(p.getNombre());
                txtPrecioUnitario.setText(String.format("%.2f", p.getPrecioU()));
                txtCantidad.setText("1"); // Pone 1 por defecto
                info("Producto encontrado: " + p.getNombre());
                calcularSubtotalItem(); // Calcula subtotal para el item
            } else {
                info("Producto no encontrado.");
                limpiarCamposProducto(); // Limpia si no encuentra
            }
        } catch (Exception e) {
            warn("Error al buscar producto: " + e.getMessage());
            e.printStackTrace();
            limpiarCamposProducto();
        }
    }


    @FXML
    private void agregarProducto() {
        if (idProductoSeleccionado == null || nvl(txtNombreProducto.getText()).isBlank()) {
            warn("Busca y selecciona un producto válido primero.");
            return;
        }

        int cant;
        try {
            cant = Integer.parseInt(nvl(txtCantidad.getText()));
            if (cant <= 0) throw new NumberFormatException();
        } catch (Exception e) { warn("Cantidad inválida (debe ser entero > 0)."); return; }

        double precio;
        try {
            precio = Double.parseDouble(nvl(txtPrecioUnitario.getText()));
            if (precio <= 0) throw new NumberFormatException();
        } catch (Exception e) { warn("Precio inválido (debe ser número > 0)."); return; }

        // Verificar Stock ANTES de agregar a la tabla
        try {
            Producto p = productoService.findProductoById(idProductoSeleccionado);
            if (p == null) {
                warn("Error: El producto seleccionado ya no existe.");
                limpiarCamposProducto();
                return;
            }
            // Sumar la cantidad si el producto ya está en la tabla
            int cantidadActualEnTabla = listaVenta.stream()
                    .filter(item -> item.getIdProducto().equals(idProductoSeleccionado))
                    .mapToInt(VentaItem::getCantidad)
                    .sum();

            if ((cantidadActualEnTabla + cant) > p.getStok()) {
                warn("Stock insuficiente para '" + p.getNombre() + "'. Stock disponible: " + p.getStok() + ", ya en carrito: " + cantidadActualEnTabla);
                return;
            }

            // Si ya existe, actualiza cantidad y subtotal en lugar de añadir
            Optional<VentaItem> itemExistente = listaVenta.stream()
                    .filter(item -> item.getIdProducto().equals(idProductoSeleccionado))
                    .findFirst();

            if (itemExistente.isPresent()) {
                VentaItem item = itemExistente.get();
                item.setCantidad(item.getCantidad() + cant);
                item.setSubtotal(item.getCantidad() * item.getPrecioUnitario()); // Recalcular subtotal
                tablaVenta.refresh(); // Refrescar la tabla para mostrar el cambio
            } else {
                // Añadir nuevo item a la tabla
                double sub = cant * precio;
                VentaItem newItem = new VentaItem(idProductoSeleccionado, txtNombreProducto.getText(), precio, cant, sub);
                listaVenta.add(newItem);
            }

            limpiarCamposProducto();
            actualizarTotalGeneral();
            info("Producto agregado/actualizado en la venta.");

        } catch (Exception e) {
            warn("Error al verificar stock: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    private void eliminarProducto() {
        VentaItem sel = tablaVenta.getSelectionModel().getSelectedItem();
        if (sel == null) { warn("Selecciona una fila para eliminar."); return; }
        listaVenta.remove(sel);
        actualizarTotalGeneral();
        info("Producto eliminado de la lista.");
    }

    @FXML
    private void vender() {
        // Validaciones de Cliente
        if (nvl(txtClienteNombre.getText()).isBlank()
                || nvl(txtClienteApellido.getText()).isBlank()
                || nvl(txtClienteDni.getText()).isBlank()) {
            warn("Completa Nombre, Apellido y DNI."); return;
        }
        String dniCliente = nvl(txtClienteDni.getText());

        // Validación de Lista de Venta
        if (listaVenta.isEmpty()) { warn("Agrega al menos un producto."); return; }

        // Validaciones de Pago
        String mp = cbMetodoPago.getValue(); // No necesita nvl si siempre hay uno seleccionado
        boolean esTarjeta = "Débito".equals(mp) || "Crédito".equals(mp);
        String nt = null;
        if (esTarjeta) {
            nt = nvl(txtNumTarjeta.getText());
            String cvv = nvl(txtCvv.getText()); // CVV no se guarda en BD generalmente, solo se valida
            if (nt.length() != 16 || !nt.matches("\\d{16}")) { warn("N° de tarjeta inválido (16 dígitos)."); return; }
            if (cvv.length() != 3 || !cvv.matches("\\d{3}")) { warn("CVV inválido (3 dígitos)."); return; }
        }

        try {
            // --- LÓGICA DE GUARDADO CLIENTE ---
            // Primero verificamos si el cliente existe
            Cliente clienteObj = clienteService.findById(dniCliente);
            if (clienteObj == null) {
                clienteObj = new Cliente();
                clienteObj.setDniruc(dniCliente);
            }
            // Actualizamos/Llenamos datos del cliente
            clienteObj.setNombres(nvl(txtClienteNombre.getText())); // Guardar nombre
            clienteObj.setApellidos(nvl(txtClienteApellido.getText())); // Guardar apellido
            clienteObj.setTipoDocumento(cbTipoDocumento.getValue());

            // Guardamos el cliente en la BD
            clienteService.save(clienteObj);


            // --- LÓGICA DE GUARDADO VENTA ---
            Venta nuevaVenta = new Venta();
            nuevaVenta.setDniCliente(dniCliente);
            nuevaVenta.setNombreCliente(nvl(txtClienteNombre.getText()));
            nuevaVenta.setApellidoCliente(nvl(txtClienteApellido.getText()));

            nuevaVenta.setMetodoPago(mp);
            nuevaVenta.setNumeroTarjeta(nt);
            nuevaVenta.setFechaVenta(LocalDateTime.now());

            Usuario usuarioLogueado = obtenerUsuarioActual();
            if (usuarioLogueado == null) {
                warn("Error: Sesión de usuario inválida. Por favor, inicia sesión de nuevo.");
                return;
            }
            nuevaVenta.setUsuario(usuarioLogueado);

            List<DetalleVenta> detalles = new ArrayList<>();
            double montoTotalCalculado = 0;

            // Volver a verificar stock justo antes de guardar (importante por concurrencia)
            for (VentaItem itemTabla : listaVenta) {
                Producto producto = productoService.findProductoById(itemTabla.getIdProducto());
                if (producto == null) {
                    throw new RuntimeException("Error crítico: Producto con ID " + itemTabla.getIdProducto() + " no encontrado durante el guardado.");
                }
                if (producto.getStok() < itemTabla.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para: " + producto.getNombre() + " al momento de guardar. Stock actual: " + producto.getStok());
                }
            }

            // Crear detalles, calcular total y preparar actualización de stock
            List<Producto> productosParaActualizar = new ArrayList<>();
            for (VentaItem itemTabla : listaVenta) {
                Producto producto = productoService.findProductoById(itemTabla.getIdProducto());

                DetalleVenta detalle = new DetalleVenta();
                detalle.setVenta(nuevaVenta);
                detalle.setProducto(producto);
                detalle.setPrecioUnitario(itemTabla.getPrecioUnitario());
                detalle.setCantidad(itemTabla.getCantidad());
                detalle.setSubtotal(itemTabla.getSubtotal());
                detalles.add(detalle);

                montoTotalCalculado += itemTabla.getSubtotal();

                // Descontar stock
                producto.setStok(producto.getStok() - itemTabla.getCantidad());
                productosParaActualizar.add(producto);
            }

            nuevaVenta.setDetalleVenta(detalles);
            nuevaVenta.setMontoTotal(montoTotalCalculado);

            // Guardar la venta
            ventaService.save(nuevaVenta);

            // Actualizar stock en BD
            for (Producto p : productosParaActualizar) {
                productoService.updateProducto(p);
            }

            generarBoleta(nuevaVenta);

            // --- FIN LÓGICA ---

            info("Venta registrada correctamente. ID: " + nuevaVenta.getIdVenta());
            tablaVenta.getItems().clear();

            txtClienteNombre.clear();
            txtClienteApellido.clear();
            txtClienteDni.clear();

            cbMetodoPago.getSelectionModel().selectFirst();
            actualizarTotalGeneral();
            limpiarCamposProducto();

        } catch (Exception e) {
            warn("Error al guardar la venta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void generarBoleta(Venta venta) {
        Long idVenta = venta.getIdVenta();
        String directorio = "Boletas";
        File dir = new File(directorio);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String nombreArchivo = directorio + File.separator + "Boleta_Venta_" + idVenta + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {

            writer.println("=========================================");
            writer.println("           SABOR SISTEMAS");
            writer.println("           BOLETA DE VENTA N° " + idVenta);
            writer.println("=========================================");

            String fecha = venta.getFechaVenta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            writer.println("FECHA: " + fecha);

            String vendedor = venta.getUsuario().getNombre_Usuario();
            writer.println("VENDEDOR: " + vendedor);
            writer.println("-----------------------------------------");

            String clienteNombreCompleto = venta.getNombreCliente() + " " + venta.getApellidoCliente();
            writer.println("CLIENTE: " + clienteNombreCompleto);
            writer.println("DNI: " + venta.getDniCliente());
            writer.println("METODO DE PAGO: " + venta.getMetodoPago());
            writer.println("-----------------------------------------");

            writer.println("DETALLES DE LA VENTA:");
            writer.printf("%-20s %-8s %-10s %-10s%n", "PRODUCTO", "CANT.", "P.UNIT.", "SUBTOTAL");
            writer.println("-----------------------------------------");

            List<DetalleVenta> detalles = venta.getDetalleVenta();
            for (DetalleVenta detalle : detalles) {
                String nombreProd = detalle.getProducto().getNombre();
                writer.printf("%-20s %-8d %-10.2f %-10.2f%n",
                        nombreProd,
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario(),
                        detalle.getSubtotal());
            }

            writer.println("-----------------------------------------");
            writer.printf("MONTO TOTAL: %.2f%n", venta.getMontoTotal());
            writer.println("=========================================");
            writer.println("       ¡GRACIAS POR SU COMPRA!");

            info("Boleta generada en: " + nombreArchivo);

        } catch (IOException e) {
            warn("Error al generar el archivo de boleta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirSelectorProducto() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/fxml/seleccionar_productos.fxml"));

            loader.setControllerFactory(applicationContext::getBean);

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Seleccionar producto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(tablaVenta.getScene().getWindow());
            stage.showAndWait();

            var sel = ProductoSeleccionadoHolder.getProducto();
            if (sel != null) {
                try {
                    Producto pReal = buscarProductoPorNombre(sel.getNombre());
                    if (pReal != null) {
                        idProductoSeleccionado = pReal.getId_producto();
                        txtNombreProducto.setText(pReal.getNombre());
                        txtPrecioUnitario.setText(String.format("%.2f", pReal.getPrecioU()));
                        txtCantidad.setText("1"); // Default a 1
                        calcularSubtotalItem();
                    } else {
                        warn("El producto seleccionado ya no está disponible.");
                        limpiarCamposProducto();
                    }
                } catch (Exception ex) {
                    warn("Error al cargar datos del producto seleccionado.");
                    ex.printStackTrace();
                    limpiarCamposProducto();
                }

            }
            ProductoSeleccionadoHolder.limpiar();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(
                    Alert.AlertType.ERROR,
                    "Error al abrir el selector de productos: " + e.getMessage()
            ).showAndWait();
        }
    }

    // --- Buscar Cliente por DNI ---
    @FXML
    private void buscarCliente() {
        String dni = nvl(txtClienteDni.getText());
        if (dni.isBlank()) {
            warn("Ingrese un DNI para buscar.");
            return;
        }

        try {
            Cliente cliente = clienteService.findById(dni);

            if (cliente != null) {
                // CORRECCIÓN: Usar getters directos para nombre y apellido
                txtClienteNombre.setText(nvl(cliente.getNombres()));
                txtClienteApellido.setText(nvl(cliente.getApellidos()));

                if (cliente.getTipoDocumento() != null) {
                    cbTipoDocumento.setValue(cliente.getTipoDocumento());
                }

                info("Cliente encontrado: " + cliente.getNombres() + " " + cliente.getApellidos());
                txtClienteNombre.requestFocus();
            } else {
                txtClienteNombre.clear();
                txtClienteApellido.clear();
                info("Cliente no registrado. Por favor ingrese los datos para crearlo.");
                txtClienteNombre.requestFocus();
            }

        } catch (Exception e) {
            System.out.println("Excepción buscando cliente (posiblemente nuevo): " + e.getMessage());
            txtClienteNombre.clear();
            txtClienteApellido.clear();
            info("Cliente nuevo. Ingrese datos.");
            txtClienteNombre.requestFocus();
        }
    }

    private void toggleCamposTarjeta() {
        if (cbMetodoPago == null) return;
        String v = cbMetodoPago.getValue();
        boolean tarjeta = "Débito".equals(v) || "Crédito".equals(v);
        if (txtNumTarjeta != null) txtNumTarjeta.setDisable(!tarjeta);
        if (txtCvv != null) txtCvv.setDisable(!tarjeta);
        if (!tarjeta) {
            if (txtNumTarjeta != null) txtNumTarjeta.clear();
            if (txtCvv != null) txtCvv.clear();
        }
    }

    private void actualizarTotalGeneral() {
        double total = listaVenta.stream().mapToDouble(VentaItem::getSubtotal).sum();
        if (txtTotal != null) txtTotal.setText(String.format("%.2f", total));
    }

    private void limpiarCamposProducto() {
        idProductoSeleccionado = null;
        if (txtNombreProducto != null) txtNombreProducto.clear();
        if (txtPrecioUnitario != null) txtPrecioUnitario.clear();
        if (txtCantidad != null) txtCantidad.clear();
        if (txtFiltroDato != null) txtFiltroDato.clear();
    }

    private void calcularSubtotalItem() {
    }


    private Usuario obtenerUsuarioActual() {
        SessionManager sm = SessionManager.getInstance();
        if (sm != null && sm.getUserId() != null) {
            try {
                return usuarioService.findById(sm.getUserId());
            } catch (Exception e) {
                System.err.println("Error buscando usuario en sesión ID " + sm.getUserId() + ": " + e.getMessage());
                return null;
            }
        }
        System.err.println("SessionManager no inicializado o sin UserId.");
        return null;
    }

    private Producto buscarProductoPorNombre(String nombre) throws Exception {
        List<Producto> productos = productoService.findAllProductos();
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }


    private void warn(String m){ lbnMsgSet(m, true); new Alert(Alert.AlertType.WARNING, m, ButtonType.OK).showAndWait(); }
    private void info(String m){ lbnMsgSet(m, false); }
    private void lbnMsgSet(String m, boolean isError){
        if (lbnMsg != null) {
            lbnMsg.setText(m);
            lbnMsg.setStyle(isError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
        }
    }
    private String nvl(String s){ return s==null? "" : s.trim(); }

    public static class VentaItem {
        private final Long idProducto;
        private String nombreProducto;
        private double precioUnitario;
        private int cantidad;
        private double subtotal;

        public VentaItem(Long idProducto, String nombreProducto, double precioUnitario, int cantidad, double subtotal) {
            this.idProducto = idProducto;
            this.nombreProducto = nombreProducto;
            this.precioUnitario = precioUnitario;
            this.cantidad = cantidad;
            this.subtotal = subtotal;
        }

        public Long getIdProducto() { return idProducto; }
        public String getNombreProducto() { return nombreProducto; }
        public double getPrecioUnitario()   { return precioUnitario; }
        public int getCantidad()    { return cantidad; }
        public double getSubtotal() { return subtotal; }

        public void setCantidad(int cantidad) { this.cantidad = cantidad; }
        public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    }
}