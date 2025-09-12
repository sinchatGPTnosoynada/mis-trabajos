package pe.edu.upeu.enums;

enum GENERO{Femenino, Masculino}
public class Estudiante {
    String codigo;
    String nombre;
    String apellido;
    GENERO genero;
    Carrera carrera;

    public Estudiante(String codigo, String nombre,
                      String apellido, GENERO genero,
                      Carrera carrera) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.carrera = carrera;
    }

    public static void main(String[] args) {
        Estudiante e=new Estudiante("202410564", "David",
                "Mamani", GENERO.Masculino, Carrera.Sistemas
        );
        System.out.println(e.codigo+" "+e.genero+" "+e.carrera);

        for (Carrera c: Carrera.values()){
            System.out.println(c);
        }

    }

}
