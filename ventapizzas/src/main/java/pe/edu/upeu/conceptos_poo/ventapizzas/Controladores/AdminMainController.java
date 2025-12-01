package pe.edu.upeu.conceptos_poo.ventapizzas.Controladores;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.conceptos_poo.ventapizzas.components.StageManager;

import java.io.IOException;
import java.util.prefs.Preferences;

@Controller
public class AdminMainController {
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @FXML private BorderPane bpAdminMain;
    @FXML private MenuBar menuBarAdmin;
    @FXML private TabPane tabPaneAdmin;
    @FXML private Menu menuEstilo;
    @FXML private Menu menuIdioma;

    private ComboBox<String> comboBoxEstilos;
    private ComboBox<String> comboBoxIdiomas;

    Preferences userPrefs = Preferences.userRoot().node("pe/edu/upeu/ventapizzas/prefs");

    @FXML
    public void initialize() {
        configurarMenuEstilo();
        configurarMenuIdioma();
        abrirTabConFXML("/fxml/gestion_productos.fxml", "Gestionar Productos");
        abrirTabConFXML("/fxml/gestion_usuarios.fxml", "Gestionar Usuarios");
        abrirTabConFXML("/fxml/gestion_venta.fxml", "Gestionar Ventas");
    }

    @FXML
    private void abrirGestionUsuarios(ActionEvent event) {
        abrirTabConFXML("/fxml/gestion_usuarios.fxml", "Gestionar Usuarios");
    }

    @FXML
    private void abrirGestionVentas(ActionEvent event) {
        abrirTabConFXML("/fxml/gestion_venta.fxml", "Gestionar Ventas");
    }

    @FXML
    private void abrirGestionProductos(ActionEvent event) {
        abrirTabConFXML("/fxml/gestion_productos.fxml", "Gestionar Productos");
    }

    @FXML
    private void abrirReporteVentas(ActionEvent event) {
        abrirTabConFXML("/fxml/reporte_ventas.fxml", "Reportes");
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
            Stage stage = StageManager.getPrimaryStage();
            if (stage == null) {
                stage = (Stage) bpAdminMain.getScene().getWindow();
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent loginRoot = fxmlLoader.load();
            Scene scene = new Scene(loginRoot);
            stage.setScene(scene);
            stage.setTitle("ventapizzas - Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void salirAplicacion(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    private void configurarMenuEstilo() {
        comboBoxEstilos = new ComboBox<>(
                FXCollections.observableArrayList(
                        "Estilo por Defecto",
                        "Estilo Oscuro",
                        "Estilo Azul",
                        "Estilo Verde",
                        "Estilo Rosado"
                )
        );
        comboBoxEstilos.setValue(userPrefs.get("appEstilo", "Estilo por Defecto"));
        comboBoxEstilos.setOnAction(e -> cambiarEstilo());

        CustomMenuItem customItemEstilo = new CustomMenuItem(comboBoxEstilos);
        customItemEstilo.setHideOnClick(false);
        menuEstilo.getItems().add(customItemEstilo);
    }

    private void configurarMenuIdioma() {
        comboBoxIdiomas = new ComboBox<>(
                FXCollections.observableArrayList(
                        "Español",
                        "Inglés"
                )
        );
        String langCode = userPrefs.get("appIdioma", "es");
        comboBoxIdiomas.setValue(langCode.equals("es") ? "Español" : "Inglés");

        comboBoxIdiomas.setOnAction(e -> cambiarIdioma());

        CustomMenuItem customItemIdioma = new CustomMenuItem(comboBoxIdiomas);
        customItemIdioma.setHideOnClick(false);
        menuIdioma.getItems().add(customItemIdioma);
    }

    private void cambiarEstilo() {
        String estiloSeleccionado = comboBoxEstilos.getValue();
        if (estiloSeleccionado == null) return;

        Scene escena = bpAdminMain.getScene();
        if (escena == null) return;

        escena.getStylesheets().clear();
        String cssPath = null;

        switch (estiloSeleccionado) {
            case "Estilo Oscuro":
                cssPath = "/css/estilo-oscuro.css";
                break;
            case "Estilo Azul":
                cssPath = "/css/estilo-azul.css";
                break;
            case "Estilo Verde":
                cssPath = "/css/estilo-verde.css";
                break;
            case "Estilo Rosado":
                cssPath = "/css/estilo-rosado.css";
                break;
            default:
                cssPath = "/css/styles.css";
                break;
        }

        if (cssPath != null && getClass().getResource(cssPath) != null) {
            escena.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            userPrefs.put("appEstilo", estiloSeleccionado);
        } else {
            userPrefs.put("appEstilo", "Estilo por Defecto");
        }
    }

    private void cambiarIdioma() {
        String idiomaSeleccionado = comboBoxIdiomas.getValue();
        if (idiomaSeleccionado == null) return;

        String langCode = "es";
        switch (idiomaSeleccionado) {
            case "Español": langCode = "es"; break;
            case "Inglés": langCode = "en"; break;
        }

        userPrefs.put("appIdioma", langCode);
        mostrarAlertaReinicioIdioma();
    }

    private void mostrarAlertaReinicioIdioma() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cambio de Idioma");
        alert.setHeaderText("Reinicia la aplicación");
        alert.setContentText("El cambio de idioma requiere reiniciar la aplicación para tener efecto completo.");
        alert.showAndWait();
    }

    private void abrirTabConFXML(String fxmlPath, String tituloTab) {
        for (Tab tab : tabPaneAdmin.getTabs()) {
            if (tab.getText().equals(tituloTab)) {
                tabPaneAdmin.getSelectionModel().select(tab);
                return;
            }
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();

            Tab nuevaPestana = new Tab(tituloTab);
            nuevaPestana.setContent(root);

            tabPaneAdmin.getTabs().add(nuevaPestana);
            tabPaneAdmin.getSelectionModel().select(nuevaPestana);

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al Cargar Módulo");
            alert.setHeaderText("No se pudo cargar la vista: " + tituloTab);
            alert.setContentText("Detalle: " + e.getMessage());
            alert.showAndWait();
        }
    }
}