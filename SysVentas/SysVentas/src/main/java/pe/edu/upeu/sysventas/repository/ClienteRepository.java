package pe.edu.upeu.sysventas.repository;

import org.springframework.stereotype.Repository;
import pe.edu.upeu.sysventas.model.Cliente;


@Repository
public interface ClienteRepository extends ICrudGenericRepository<Cliente,String > {
}
