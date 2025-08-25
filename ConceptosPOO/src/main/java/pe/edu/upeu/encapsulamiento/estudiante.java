package pe.edu.upeu.encapsulamiento;

public class estudiante {

    private String codigo;
    private String carreda;

    public void trabajo(){

        System.out.println("Estudiar, investigar en la carreda de: " + carreda);


    }

    public estudiante() {
    }

    public estudiante(String codigo, String carreda) {
        this.codigo = codigo;
        this.carreda = carreda;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCarreda() {
        return carreda;
    }

    public void setCarreda(String carreda) {
        this.carreda = carreda;
    }
}
