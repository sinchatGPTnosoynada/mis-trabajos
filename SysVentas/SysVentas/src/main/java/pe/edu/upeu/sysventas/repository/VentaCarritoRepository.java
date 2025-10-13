package pe.edu.upeu.sysventas.repository;

import org.springframework.stereotype.Repository;
import pe.edu.upeu.sysventas.model.CarritoVenta;

@Repository

public interface VentaCarritoRepository extends ICrudGenericRepository<CarritoVenta, Long> {
}
