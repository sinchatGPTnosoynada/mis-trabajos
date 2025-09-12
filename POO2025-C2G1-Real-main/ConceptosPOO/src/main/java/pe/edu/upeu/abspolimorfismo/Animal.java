package pe.edu.upeu.abspolimorfismo;

public abstract class Animal {
    protected String tipo="XXX";
    public abstract void emitirSonido();

    public void dormir(){
        System.out.println("Zzz...zzz!");
    }

}
