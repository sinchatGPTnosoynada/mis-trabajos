package sinchatgpt.nosoy.nada.pizzaHut.repository;

import org.springframework.stereotype.Repository;
import sinchatgpt.nosoy.nada.pizzaHut.model.Cliente;


@Repository
public interface ClienteRepository extends ICrudGenericRepository<Cliente,String > {
}
