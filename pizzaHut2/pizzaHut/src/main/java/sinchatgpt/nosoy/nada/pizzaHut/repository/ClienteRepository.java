package sinchatgpt.nosoy.nada.pizzaHut.repository;

import org.springframework.stereotype.Repository;
import pe.edu.upeu.sysventas.model.Cliente;


@Repository
public interface ClienteRepository extends ICrudGenericRepository<Cliente,String > {
}
