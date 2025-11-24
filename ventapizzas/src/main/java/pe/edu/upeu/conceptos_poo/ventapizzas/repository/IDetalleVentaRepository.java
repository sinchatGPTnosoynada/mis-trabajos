package pe.edu.upeu.conceptos_poo.ventapizzas.repository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.DetalleVenta;

@Repository
public interface IDetalleVentaRepository extends ICrudGenericoRepository<DetalleVenta, Long> {
}