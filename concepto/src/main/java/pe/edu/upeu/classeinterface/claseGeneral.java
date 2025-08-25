package pe.edu.upeu.classeinterface;

public class claseGeneral {

    public static void main (String[] args){

            animal a,b;

            a = new loro();
            a.emitirSonido();
            a.dormir();

            b = new perro();
            b.emitirSonido();
            b.dormir();


    }

}
