package sinchatgpt.nosoy.nada.pizzaHut.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import sinchatgpt.nosoy.nada.pizzaHut.components.ColumnInfo;
import sinchatgpt.nosoy.nada.pizzaHut.components.TableViewHelper;
import sinchatgpt.nosoy.nada.pizzaHut.components.Toast;
import sinchatgpt.nosoy.nada.pizzaHut.dto.ComboBoxOption;
import sinchatgpt.nosoy.nada.pizzaHut.enums.TipoDocumento;
import sinchatgpt.nosoy.nada.pizzaHut.model.Cliente;
import sinchatgpt.nosoy.nada.pizzaHut.model.Producto;
import sinchatgpt.nosoy.nada.pizzaHut.service.IClienteService;
import sinchatgpt.nosoy.nada.pizzaHut.service.ProductoIService;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Controller
public class ClienteController {

    Cliente formulario;

    @FXML
    private TextField txtDniRuc, txtNombres;

    @FXML
    private ComboBox<TipoDocumento> cbxTipoDocumento;

    @FXML
    private ComboBox<String> cbxRepLegal;

    @FXML
    private TextField txtFiltroDato;

    ObservableList<Cliente> listarCliente;

    @FXML private TableView<Cliente> tableView;
    @FXML private Label lbnMsg;
    @FXML
    ComboBox<ComboBoxOption> cbtipoDocumento;

    @Autowired

    IClienteService ps;

    private void filtrarClientes(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tableView.getItems().clear();
            tableView.getItems().addAll(listarCliente);
        } else {
            String lowerCaseFilter = filtro.toLowerCase();
            List<Cliente> clientesFiltrados = listarCliente.stream()
                    .filter(cliente -> {
                        if (cliente.getDniruc() != null && cliente.getDniruc().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        if (cliente.getNombres() != null && cliente.getNombres().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        if (cliente.getRepLegal() != null && cliente.getRepLegal().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        if (cliente.getTipoDocumento() != null &&
                                cliente.getTipoDocumento().toString().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
            tableView.getItems().clear();
            tableView.getItems().addAll(clientesFiltrados);
        }
    }

    public void listarCliente() {
        try {
            tableView.getItems().clear();
            listarCliente = FXCollections.observableArrayList(ps.findAll());
            tableView.getItems().addAll(listarCliente);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void editarCliente(Cliente clienteSeleccionado) {
        if (clienteSeleccionado != null) {
            txtDniRuc.setText(clienteSeleccionado.getDniruc());
            txtNombres.setText(clienteSeleccionado.getNombres());


            cbxRepLegal.getSelectionModel().select(clienteSeleccionado.getRepLegal());
            cbxTipoDocumento.getSelectionModel().select(clienteSeleccionado.getTipoDocumento());

            lbnMsg.setText("Editando cliente: " + clienteSeleccionado.getNombres());
        } else {
            lbnMsg.setText("No se ha seleccionado ningún cliente.");
        }
    }


    public void guardarCliente() {
        Cliente cliente = new Cliente();
        cliente.setDniruc(txtDniRuc.getText());
        cliente.setNombres(txtNombres.getText());
        cliente.setRepLegal(cbxRepLegal.getValue());
        cliente.setTipoDocumento(cbxTipoDocumento.getValue());

        ps.save(cliente);

        listarCliente();

        lbnMsg.setText("Cliente guardado correctamente");
        lbnMsg.setStyle("-fx-text-fill: green;");
    }

    @FXML
    public void validarFormulario() {
        Cliente clienteForm = new Cliente();

        clienteForm.setDniruc(txtDniRuc.getText());
        clienteForm.setNombres(txtNombres.getText());
        clienteForm.setRepLegal(cbxRepLegal.getValue());
        clienteForm.setTipoDocumento(cbxTipoDocumento.getValue());


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Cliente>> violaciones = validator.validate(clienteForm);

        List<ConstraintViolation<Cliente>> violacionesOrdenadas = violaciones.stream()
                .sorted(Comparator.comparing(v -> v.getPropertyPath().toString()))
                .toList();

        if (violacionesOrdenadas.isEmpty()) {
            procesarFormulario(clienteForm);
        } else {
            mostrarErroresValidacion(violacionesOrdenadas);
        }
    }

    private void mostrarErroresValidacion(List<ConstraintViolation<Cliente>> violaciones) {
        StringBuilder errores = new StringBuilder();
        for (ConstraintViolation<Cliente> v : violaciones) {
            errores.append(v.getPropertyPath()).append(": ").append(v.getMessage()).append("\n");
        }
        lbnMsg.setText(errores.toString());
        lbnMsg.setStyle("-fx-text-fill: red;");
    }


    private void procesarFormulario(Cliente clienteForm) {
        ps.save(clienteForm);
        listarCliente();
        lbnMsg.setText("Cliente guardado correctamente");
        lbnMsg.setStyle("-fx-text-fill: green;");

        nuevoCliente();
    }




    @FXML
    private void nuevoCliente() {
        txtDniRuc.clear();
        txtNombres.clear();
        cbxRepLegal.getSelectionModel().clearSelection();
        cbxTipoDocumento.getSelectionModel().clearSelection();
        lbnMsg.setText("");
    }


    @FXML
    private void refrescarTabla() {
        listarCliente();
        lbnMsg.setText("Tabla actualizada");
        lbnMsg.setStyle("-fx-text-fill: green;");
    }

    @FXML
    private void eliminarCliente() {
        Cliente cliente = tableView.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            ps.deleteById(cliente.getDniruc());
            lbnMsg.setText("Cliente eliminado correctamente");
            lbnMsg.setStyle("-fx-text-fill: green;");
            listarCliente();
        } else {
            lbnMsg.setText("Seleccione un cliente para eliminar");
            lbnMsg.setStyle("-fx-text-fill: red;");
        }
    }




}