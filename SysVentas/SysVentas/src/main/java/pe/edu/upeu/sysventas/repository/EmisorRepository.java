package pe.edu.upeu.sysventas.repository;

import org.springframework.stereotype.Repository;
import pe.edu.upeu.sysventas.model.Emisor;


@Repository
public interface EmisorRepository extends ICrudGenericRepository<Emisor,Long> {
}
