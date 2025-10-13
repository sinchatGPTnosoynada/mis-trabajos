package pe.edu.upeu.sysventas.repository;

import org.springframework.stereotype.Repository;
import pe.edu.upeu.sysventas.model.Venta;


@Repository
public interface VentaRepository extends ICrudGenericRepository<Venta,Long> {
}
