package pe.edu.upeu.herencia;

public class Carro extends Vehiculo {
    String modelo="Mustang";
    String placa="PE-14454";
    String color="Blanco";

    void caracteristicas(){
        System.out.println("Las caracteriticas de este carro son:");
        System.out.println("Marca: "+marca); //atributo heredado
        System.out.println("Modelo: "+modelo);
        System.out.println("Modelo: "+color);
        System.out.println("Modelo: "+placa);
    }

    public static void main(String[] args) {
        Carro carro = new Carro();
        carro.caracteristicas();
        carro.sonido();  //metodo heredado
    }
}
