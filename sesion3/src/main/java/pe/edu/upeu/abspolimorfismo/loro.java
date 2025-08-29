package pe.edu.upeu.abspolimorfismo;

public class loro extends animal {

    protected String tipo="1";

    @Override
    public void emitirsonido(){

        System.out.println("hey no te duermas");

    }

    @Override
    public void dormir() {

        System.out.println(super.tipo);
        System.out.println(this.tipo);
        System.out.println("mo molestar");

    }
}