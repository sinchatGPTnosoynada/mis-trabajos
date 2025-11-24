package pe.edu.upeu.asistencia.control;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.asistencia.dto.PersonaDto;
import pe.edu.upeu.asistencia.enums.Ingredientes;
import pe.edu.upeu.asistencia.enums.Tamaño;
import pe.edu.upeu.asistencia.modelo.Participante;
import pe.edu.upeu.asistencia.servicio.ParticipanteServicioI;

import java.util.Set;

@Controller
public class ParticipanteController {
    @FXML
    private ComboBox<Ingredientes> cbxIngredientes;
    @FXML
    private ComboBox<Tamaño> cbxTamaño;
    @FXML
    private TextField txtCodigo, txtNombrePizza, txtPrecio;


    ObservableList<Participante> participantes;
    @FXML
    TableView<Participante> tableRegPart;
    private Validator validator;

    @Autowired
    ParticipanteServicioI psi;

    TableColumn<Participante, Void> opcColumn;
    TableColumn<Participante, String> codigoColumn, nombreColumn, precioColumn, ingredientesColumn, tamañoColumn;
    private int indexEdit=-1;

    @FXML
    public void initialize() {
        //--------------------inicio validacion--------------
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        // Limpiar estilos de error al escribir en los campos
        txtCodigo.textProperty().addListener((obs, oldV, newV) -> clearErrorStyle(txtCodigo));
        txtNombrePizza.textProperty().addListener((obs, oldV, newV) -> clearErrorStyle(txtNombrePizza));
        txtPrecio.textProperty().addListener((obs, oldV, newV) -> clearErrorStyle(txtPrecio));
        //--------------------fin validacion--------------
        cbxIngredientes.getItems().setAll(Ingredientes.values());
        cbxIngredientes.getSelectionModel().selectLast();

        Ingredientes seleccion = cbxIngredientes.getSelectionModel().getSelectedItem();
        System.out.println("Seleccionado: " + seleccion);

        cbxTamaño.getItems().setAll(Tamaño.values());
        cbxTamaño.getSelectionModel().selectLast();
        definirColumnas();
        listarEstudiantes();

    }


    private void listarEstudiantes(){
        codigoColumn.setCellValueFactory(cellData ->cellData.getValue().getCodigo());
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().getNombre());
        precioColumn.setCellValueFactory(cellData -> cellData.getValue().getPrecio());
        ingredientesColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIngredientes().toString())
        );
        tamañoColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipoParticipante().toString())
        );
        addActionButtonsToTable();
        participantes = FXCollections.observableArrayList(psi.findAll());

        tableRegPart.setItems(participantes);
    }

    @FXML
    public void buscarPorDni(){
        PersonaDto personaDto = psi.findByDni(txtCodigo.getText());
        if(personaDto!=null){
            txtNombrePizza.setText(personaDto.getNombre());
            txtPrecio.setText(personaDto.getApellidoPaterno()+" "+personaDto.getApellidoMaterno());
        }else{
            txtNombrePizza.setText("");
            txtPrecio.setText("");
        }
    }

    public void limpiarFormulario(){
        txtCodigo.clear();
        txtNombrePizza.clear();
        txtPrecio.clear();
        cbxIngredientes.getSelectionModel().clearSelection();
        cbxTamaño.getSelectionModel().clearSelection();
    }

    public void limpiarEstilosFormulario(){
        clearErrorStyle(txtCodigo);
        clearErrorStyle(txtNombrePizza);
        clearErrorStyle(txtPrecio);
    }

    @FXML
    public void registrar(){
        //inicio validadcion
        limpiarEstilosFormulario();
        //fin

        Participante p = new Participante();
        p.setCodigo(new SimpleStringProperty(txtCodigo.getText()));
        p.setNombre(new SimpleStringProperty(txtNombrePizza.getText()));
        p.setPrecio(new SimpleStringProperty(txtPrecio.getText()));
        p.setIngredientes(cbxIngredientes.getValue());
        p.setTipoParticipante(cbxTamaño.getValue());

        //inicio Validacion
        Set<ConstraintViolation<Participante>> violations = validator.validate(p); //Validacion
        if(violations.iterator().hasNext()) {
            ConstraintViolation<Participante> firstViolation = violations.iterator().next();
            String errorMessage = firstViolation.getMessage();
        }
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Participante> violation : violations) {
                // Mapear el error al campo de texto correspondiente

                String fieldName = violation.getPropertyPath().toString();

                switch (fieldName) {
                    case "dni":
                        System.out.println("Error:"+violation.getMessage());
                        setErrorStyle(txtCodigo);
                        txtCodigo.requestFocus();
                        break;
                    case "nombre":
                        System.out.println("Error:"+violation.getMessage());
                        setErrorStyle(txtNombrePizza);
                        txtNombrePizza.requestFocus();
                        break;
                    case "apellidos":
                        System.out.println("Error:"+violation.getMessage());
                        setErrorStyle(txtPrecio);
                        txtPrecio.requestFocus();
                        break;
                }
            }
            //Toast.showToast(currentStage, errorMessage);
        }else {// fin validacion
            if (indexEdit != -1) {
                psi.update(p, indexEdit);
                indexEdit=-1;
            } else {
                psi.save(p);
            }
            limpiarFormulario();
        }


        listarEstudiantes();
    }

    @FXML
    public void editar(Participante p, int index){
        txtCodigo.setText(p.getCodigo().getValue());
        txtNombrePizza.setText(p.getNombre().getValue());
        txtPrecio.setText(p.getPrecio().getValue());
        cbxIngredientes.getSelectionModel().select(p.getIngredientes());
        cbxTamaño.getSelectionModel().select(p.getTipoParticipante());
       indexEdit=index;
    }

    @FXML
    public void eliminar(int index){
        //Participante p = tableRegPart.getSelectionModel().getSelectedItem();
        psi.delete(index);
        listarEstudiantes();
    }

    public void definirColumnas(){
        codigoColumn = new TableColumn<>("codigo");
        nombreColumn = new TableColumn<>("Nombre");
        precioColumn = new TableColumn<>("precio");
        precioColumn.setMinWidth(150);
        ingredientesColumn = new TableColumn<>("ingredientes");
        tamañoColumn = new TableColumn<>("tamaño");
        opcColumn = new TableColumn<>("Opciones");
        opcColumn.setMinWidth(150);
        tableRegPart.getColumns().addAll(codigoColumn, nombreColumn, precioColumn, ingredientesColumn, tamañoColumn, opcColumn);
    }

    private void addActionButtonsToTable() {
        Callback<TableColumn<Participante, Void>, TableCell<Participante, Void>>
                cellFactory = param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            {
                editButton.setOnAction(event -> {
                    Participante p = getTableView().getItems().get(getIndex());
                    editar(p, getIndex());
                });
                deleteButton.getStyleClass().setAll("button", "delete-button");
                deleteButton.setOnAction(event -> {
                    eliminar(getIndex());
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(editButton, deleteButton);
                    buttons.setSpacing(10);
                    setGraphic(buttons);
                }
            }
        };
        opcColumn.setCellFactory(cellFactory);
    }


    //---------------i---------------------
    private void clearErrorStyle(TextField field) {
        field.getStyleClass().remove("error-field");
    }

    private void setErrorStyle(TextField field) {
        if (!field.getStyleClass().contains("error-field")) {
            field.getStyleClass().add("error-field");
        }
    }
    //-----------f-------------------------

    }
