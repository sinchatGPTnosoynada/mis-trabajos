package pe.edu.upeu.encapsulamiento;

public class clase_general {

    public static void probar (){

        estudiante Estudiante = new estudiante();

        Estudiante.setCarreda("ing. sistemas");
        Estudiante.setCodigo("20389898");

        Estudiante.trabajo();

    }

    public static void probarjugador(){

        jugador cs = new jugador();
        cs.setEdad(22);
        cs.setNombre("pedro");
        cs.setApellido("mamani");
        cs.setPosicion("atras");
        cs.setNumeroCam(11);

        System.out.println(cs);

    }

    public static void main(String [] args){
        
        persona Persona = new persona();

        Persona.setNombre("david");
        Persona.setEdad(18);
        Persona.saludo();
        persona.setGenero("M");

        probar();
        probarjugador();
    }
    
}
