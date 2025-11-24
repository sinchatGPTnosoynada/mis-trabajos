package pe.edu.upeu.asistencia.modelo;

import jakarta.validation.constraints.NotBlank;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import lombok.*;
import pe.edu.upeu.asistencia.componente.validacion.CODIGO;
import pe.edu.upeu.asistencia.enums.Ingredientes;
import pe.edu.upeu.asistencia.enums.Tamaño;

@AllArgsConstructor
@NoArgsConstructor
//@Data
@Setter
@Getter
public class Participante {
    @NotBlank(message = "El codigo no puede estar vacío.")
    @CODIGO
    private StringProperty codigo;
    @NotBlank(message = "El nombre no puede estar vacío.")
    private StringProperty nombre;
    @NotBlank(message = "precio no puede estar vacío.")
    private StringProperty precio;
    private Ingredientes ingredientes;
    private Tamaño tipoParticipante;
    private BooleanProperty estado= new SimpleBooleanProperty(true);

}
