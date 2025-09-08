package pe.edu.upeu.asistencia.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.asistencia.enums.Carrera;
import pe.edu.upeu.asistencia.enums.TipoParticipante;
import pe.edu.upeu.asistencia.modelo.Participante;
import pe.edu.upeu.asistencia.servicio.ParticipanteServicioI;

@Controller
public class ParticipanteController {

    @FXML
    private ComboBox<Carrera> cbxCarrera;

    @FXML
    private ComboBox<TipoParticipante> cbxTipoParticipante;

    @FXML
    private TextField txtNombres , txtDni , txtApellidos;

    @FXML
    private TableView<Participante> tableRegPart;
    ObservableList<Participante> participantes;

    @Autowired
    ParticipanteServicioI ps;

    TableColumn<Participante, String> dniCol , nombreCol , ApellidoCol , CarreraCol , TipoParticipanteCol;

    TableColumn<Participante, Void> opCol;

    int indexEdirt = -1;

    @FXML

    public void initialize(){
        cbxCarrera.getItems().addAll(Carrera.values());
        cbxTipoParticipante.getItems().addAll(TipoParticipante.values());

        cbxCarrera.getSelectionModel().select(4);
        Carrera carrera = cbxCarrera.getSelectionModel().getSelectedItem();
        System.out.println(carrera.name());
        definirColumnas();
        listarPartipantes();
    }

    public void limpiarFormulario(){

        cbxCarrera.getSelectionModel().clearSelection();
        txtNombres.setText("");
        txtApellidos.setText("");
        txtDni.setText("");
        cbxCarrera.getSelectionModel().clearSelection();
        cbxTipoParticipante.getSelectionModel().clearSelection();

    }

    @FXML
    public void RegistrarParticipante(){

        Participante p = new Participante();

        p.setCarrera(cbxCarrera.getSelectionModel().getSelectedItem());
        p.setTipoParticipante(cbxTipoParticipante.getSelectionModel().getSelectedItem());
        p.setDni(new SimpleStringProperty(txtDni.getText()));
        p.setNombre(new SimpleStringProperty(txtNombres.getText()));
        p.setApellidos(new SimpleStringProperty(txtApellidos.getText()));
        p.setCarrera(cbxCarrera.getSelectionModel().getSelectedItem());
        p.setTipoParticipante(cbxTipoParticipante.getSelectionModel().getSelectedItem());

        if (indexEdirt == -1) {

            ps.save(p);

        }
        else{

            ps.update(p,indexEdirt);

            indexEdirt =-1;

        }

        limpiarFormulario();
        listarPartipantes();

    }

    public void definirColumnas(){

        dniCol = new TableColumn<>("DNI");
        nombreCol = new TableColumn<>("Nombre");
        ApellidoCol = new TableColumn<>("Apellido");
        CarreraCol = new TableColumn<>("Carrera");
        TipoParticipanteCol = new TableColumn<>("TipoParticipanteCol");

        opCol = new TableColumn<>("opciones");

        tableRegPart.getColumns().addAll(dniCol, nombreCol,ApellidoCol , CarreraCol , TipoParticipanteCol, opCol);

    }


    public void listarPartipantes(){

        dniCol.setCellValueFactory(cellData -> cellData.getValue().getDni());

        nombreCol.setCellValueFactory(cellData -> cellData.getValue().getNombre());

        ApellidoCol.setCellValueFactory(celdata-> celdata.getValue().getApellidos());

        CarreraCol.setCellValueFactory(celdata-> new SimpleStringProperty(celdata.getValue().getCarrera().toString()));

        agregarAcciones();

        participantes = FXCollections.observableList(ps.findAll());

        tableRegPart.setItems(participantes);

    }

    public void  eliminarParticipante (int index){

        ps.delete(index);
        listarPartipantes();

    }

    public void editarParticipante (Participante p , int index){

        txtDni.setText(p.getDni().toString());
        txtNombres.setText(p.getNombre().toString());
        txtApellidos.setText(p.getApellidos().toString());
        cbxTipoParticipante.getSelectionModel().select(p.getTipoParticipante());
        cbxCarrera.getSelectionModel().select(p.getCarrera());
        indexEdirt =index;

    }

    public void agregarAcciones(){

        Callback<TableColumn<Participante, Void>, TableCell<Participante, Void>>
                cellFactory= param -> new TableCell<>(){

            private final Button btnEdit = new Button ("Editar");

            private final Button btnDelet = new Button("Eliminar");

            {

                btnEdit.setOnAction( event -> {

                    Participante p = getTableView().getItems().get(getIndex());

                    editarParticipante(p, getIndex());

                });

                btnDelet.setOnAction( event ->{

                    eliminarParticipante(getIndex());

                });

            }

            @Override
            public void updateItem(Void item, boolean empty){

                super.updateItem(item, empty);

                if (empty){

                    setGraphic(null);

                }
                else {

                    HBox hBox = new HBox(btnDelet , btnEdit);

                    hBox.setSpacing(10);

                    setGraphic(hBox);

                }

            }

        };

        opCol.setCellFactory(cellFactory);

    }

}
