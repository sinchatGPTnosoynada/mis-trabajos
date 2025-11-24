package sinchatgpt.nosoy.nada.pizzaHut.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sinchatgpt.nosoy.nada.pizzaHut.model.CarritoVenta;

import java.util.List;

@Repository

public interface VentaCarritoRepository extends ICrudGenericRepository<CarritoVenta, Long> {


    @Query(value = "SELECT c.* FROM upeu_vent_carrito c WHERE c.dniruc=:dniruc", nativeQuery = true)
    List<CarritoVenta> listaCarritoCliente(@Param("dniruc") String dniruc);


    void deleteByDniruc(String dniruc);


}
