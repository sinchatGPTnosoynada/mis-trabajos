package sinchatgpt.nosoy.nada.pizzaHut.repository;


import org.springframework.stereotype.Repository;
import sinchatgpt.nosoy.nada.pizzaHut.model.Marca;

@Repository
public interface MarcaRepository extends ICrudGenericRepository<Marca,Long> {
}
