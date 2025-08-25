package pe.edu.upeu.encapsulamiento;

public class persona {

    String Nombre;

    int Edad;

    static String genero;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }

    public static String getGenero() {
        return genero;
    }

    public static void setGenero(String genero) {
        persona.genero = genero;
    }

    public void saludo() {

    System.out.println("hola mi nombre es "+Nombre+"y mi edad es"+Edad  );

    }
}
