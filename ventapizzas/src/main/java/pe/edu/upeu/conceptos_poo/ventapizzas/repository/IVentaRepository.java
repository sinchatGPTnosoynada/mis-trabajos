package pe.edu.upeu.conceptos_poo.ventapizzas.repository;

import org.springframework.stereotype.Repository;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.Venta;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IVentaRepository extends ICrudGenericoRepository<Venta, Long> {
    List<Venta> findByFechaVentaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}