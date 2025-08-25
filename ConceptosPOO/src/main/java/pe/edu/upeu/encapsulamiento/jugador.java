package pe.edu.upeu.encapsulamiento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor


@Getter
@Setter
public class jugador {

    private String nombre;
    private String apellido;
    private int edad;
    private String posicion;
    private int numeroCam;

@Override
    public String toString() {
        return "el jugador tiene estas caracteristicas:\n"+"nombre"+nombre+"\n"+
                "Apellido"+apellido+"\n"+
                "Edad"+edad+"\n"+
                "Posicion"+posicion+"\n"+
                "camiseta"+numeroCam+"\n";

    }
}
