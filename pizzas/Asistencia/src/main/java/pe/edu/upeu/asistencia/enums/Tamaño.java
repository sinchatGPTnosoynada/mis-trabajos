package pe.edu.upeu.asistencia.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum Tamaño {
    GRANDE("grande"), NORMAL("normal"), PEQUEÑO("pequeño");

    private String descripcion;

    @Override
    public String toString() {
        return descripcion;
    }
}
