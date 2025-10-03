package pe.edu.upeu.asistencia.modelo;


import jakarta.persistence.*;
import lombok.*;
import pe.edu.upeu.asistencia.enums.Carrera;
import pe.edu.upeu.asistencia.enums.TipoParticipante;

@AllArgsConstructor
@NoArgsConstructor
//@Data
@Setter
@Getter
@Entity
@Table(name = "participante")
public class Participante {

    @Id
    private String  dni;
    private String nombre;
    private String apellidos;
    @Enumerated(EnumType.STRING)
    private Carrera carrera;
    @Enumerated(EnumType.STRING)
    private TipoParticipante tipoParticipante;
    private Boolean estado;

}
