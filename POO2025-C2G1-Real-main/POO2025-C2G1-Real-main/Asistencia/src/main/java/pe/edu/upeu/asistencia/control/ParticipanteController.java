package pe.edu.upeu.asistencia.control;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private ComboBox<TipoParticipante> cbxTipoParticopante;

    @Autowired
    ParticipanteServicioI ps;

    @FXML
    private TableView<Participante> tableregPart;
    ObservableList<Participante> participantes;



    @FXML
    private void initialize(){

        cbxCarrera.getItems().addAll(Carrera.values());

        cbxTipoParticopante.getItems().addAll(TipoParticipante.values());

        cbxCarrera.getSelectionModel().selectLast();

        Carrera carrera = cbxCarrera.getSelectionModel().getSelectedItem();
        System.out.println(carrera.name());
        listarParticipantes();


    }

    public void listarParticipantes(){

        TableColumn<Participante, String> dniCol = new TableColumn<>("dni");
        dniCol.setCellValueFactory(cellData -> cellData.getValue().getDni());

        TableColumn<Participante, String> nombreCol = new TableColumn<>("nombre");
        dniCol.setCellValueFactory(cellData -> cellData.getValue().getDni());

        participantes = FXCollections.observableList(ps.findAll());
        tableregPart.getColumns().addAll(dniCol, nombreCol);
        tableregPart.setItems(participantes);

    }

}


