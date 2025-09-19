package pe.edu.upeu.asistencia.repositorio;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import pe.edu.upeu.asistencia.dto.PersonaDto;
import pe.edu.upeu.asistencia.enums.Ingredientes;
import pe.edu.upeu.asistencia.enums.Tamaño;
import pe.edu.upeu.asistencia.modelo.Participante;
import pe.edu.upeu.asistencia.utils.ConsultaDNI;

import java.util.ArrayList;
import java.util.List;

public abstract class ParticipanteRepository extends ConsultaDNI {
   protected List<Participante> participantes =new ArrayList<>();

   public List<Participante> findAll(){
       participantes.add(new Participante(
               new SimpleStringProperty("101"),
               new SimpleStringProperty("pizza"),
               new SimpleStringProperty("S/15"),
               Ingredientes.TOMILLO,
               Tamaño.NORMAL,
               new SimpleBooleanProperty(true)
                ));
       participantes.add(new Participante(
               new SimpleStringProperty("102"),
               new SimpleStringProperty("pizza"),
               new SimpleStringProperty("S/25"),
               Ingredientes.OREGANO,
               Tamaño.GRANDE,
               new SimpleBooleanProperty(true)
       ));
       participantes.add(new Participante(
               new SimpleStringProperty("103"),
               new SimpleStringProperty("pizza"),
               new SimpleStringProperty("S/11"),
               Ingredientes.SAL,
               Tamaño.PEQUEÑO,
               new SimpleBooleanProperty(true)
       ));
       return participantes;
   }

   public PersonaDto findByDni(String dni){
       return consultarDNI(dni);
   }


}
