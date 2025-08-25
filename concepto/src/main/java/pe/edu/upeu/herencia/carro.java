package pe.edu.upeu.herencia;

public class carro  extends vehiculo{

    String modelo="muslang";
    String placa = "20220";
    String color = "negro";

    void caracteristicas(){

        System.out.println("Las caracteristicas del carro son: ");
        System.out.println("marca :"+marca);
        System.out.println("modelo :"+modelo);
        System.out.println("color :"+color);
        System.out.println("placa :"+placa);

    }

    public static void main (String [] args){

        carro cs = new carro();
        cs.caracteristicas();
        cs.sonido();



    }

}
