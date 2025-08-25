package pe.edu.upeu.classeinterface;

public class perro implements animal{
    @Override
    public void emitirSonido() {
        System.out.println("que me ves guau");
    }

    @Override
    public void dormir() {
        System.out.println("zzz...zzz");
    }
}
