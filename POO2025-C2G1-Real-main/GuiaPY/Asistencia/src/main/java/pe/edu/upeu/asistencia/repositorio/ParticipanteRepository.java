package pe.edu.upeu.asistencia.repositorio;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import pe.edu.upeu.asistencia.dto.PersonaDto;
import pe.edu.upeu.asistencia.enums.Carrera;
import pe.edu.upeu.asistencia.enums.TipoParticipante;
import pe.edu.upeu.asistencia.modelo.Participante;
import pe.edu.upeu.asistencia.utils.ConsultaDNI;

import java.util.ArrayList;
import java.util.List;

public abstract class ParticipanteRepository extends ConsultaDNI {
   protected List<Participante> participantes =new ArrayList<>();

   public List<Participante> findAll(){
       participantes.add(new Participante(
               new SimpleStringProperty("43631917"),
               new SimpleStringProperty("Juan"),
               new SimpleStringProperty("Apaza"),
               Carrera.SISTEMAS,
               TipoParticipante.ASISTENTE,
               new SimpleBooleanProperty(true)
                ));
       return participantes;
   }

   public PersonaDto findByDni(String dni){
       return consultarDNI(dni);
   }


}
