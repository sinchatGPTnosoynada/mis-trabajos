package pe.edu.upeu.asistencia.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Carrera {
    SISTEMAS(Facultad.FIA, "Sistemas"),
    CIVIL(Facultad.FIA, "Civil"),
    AMBIENTAL(Facultad.FIA,  "Ambiental"),

    ADMINISTRACION(Facultad.FCE,  "Administración"),

    NUTRICION(Facultad.FCS,   "Nutrición"),

    EDUCACION(Facultad.FACIHED, "Educación"),

    GENERAL(Facultad.GENERAL, "General"),;

    private Facultad facultad;
    private String descripcion;

    @Override
    public String toString() {
        return descripcion;
    }


}
