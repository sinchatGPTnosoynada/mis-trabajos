package pe.edu.upeu.conceptos_poo.ventapizzas.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ss_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre_user", nullable = false, unique = true)
    private String nombre_Usuario;

    @Column(name = "clave", nullable = false)
    private String clave;

    @Column(name = "rol", nullable = false)
    private String rol = "Cliente";

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_perfil", referencedColumnName = "id_perfil", nullable = false)
    private Perfil idPerfil;
}
