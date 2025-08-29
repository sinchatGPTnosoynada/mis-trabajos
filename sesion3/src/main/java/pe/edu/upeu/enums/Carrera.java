package pe.edu.upeu.enums;

enum FACULTAD{

    FIA,
    FCE,
    FACIED,
    FCS,

}

public enum Carrera {

        Sistemas (FACULTAD.FIA),
        civil(FACULTAD.FIA),
        ambiental(FACULTAD.FIA),
        arquitectura(FACULTAD.FIA),


        administraciom(FACULTAD.FIA),
        contabilidad(FACULTAD.FIA),
        ;

        FACULTAD facultad;

        Carrera(FACULTAD facultad){

            this.facultad = facultad;

        }
}
