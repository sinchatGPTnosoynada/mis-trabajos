package pe.edu.upeu.enums;

enum  GENERO{

    Femenino , Masculino

}

public class estudiante {

        String codigo;
        String nombre;
        String apellido;
        GENERO genero;
        Carrera carrera;

    public estudiante(String codigo, String nombre,
                          String apellido, GENERO genero,
                          Carrera carrera) {

            this.codigo = codigo;
            this.nombre = nombre;
            this.apellido = apellido;
            this.genero = genero;
            this.carrera = carrera;

    }

    public static void main(String[] args) {

        estudiante e = new estudiante("202514022"
                , "francisco", "apaza"
                , GENERO.Masculino, Carrera.Sistemas);

        System.out.println(e.codigo + " " + e.genero + " " + e.carrera);


        for (Carrera c : Carrera.values()) {
            System.out.println(c);

        }

    }
}