package pe.edu.upeu.asistencia.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum Ingredientes {
    SAL(Facultad.FIA, "sal"),

    ALBAHANA(Facultad.FIA, "albahaca"),

    MEJORANA(Facultad.FIA, "mejorana"),

    OREGANO(Facultad.FCE, "orégano"),

    TOMILLO(Facultad.FCS, "tomillo"),

    AJO(Facultad.FACIHED, "ajo"),

    AJI_DULCE(Facultad.GENERAL, "ají dulce"),

    TODOS_LOS_INGREDIENTES(Facultad.FACIHED, "todos los ingredientes");



    private Facultad facultad;
    private String descripcion;

    @Override
    public String toString() {
        return descripcion;
    }
}
