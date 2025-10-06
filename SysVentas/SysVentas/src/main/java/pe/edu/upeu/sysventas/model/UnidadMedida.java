package pe.edu.upeu.sysventas.model;

import jakarta.persistence.*;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "upeu_unid_medida")
public class UnidadMedida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idUnidad;

    @Column(nullable = false, length = 60)
    private String nombreMedida;
}