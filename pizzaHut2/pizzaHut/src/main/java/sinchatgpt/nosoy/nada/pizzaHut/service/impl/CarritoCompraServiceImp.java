package sinchatgpt.nosoy.nada.pizzaHut.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sinchatgpt.nosoy.nada.pizzaHut.model.CarritoCompra;
import sinchatgpt.nosoy.nada.pizzaHut.repository.CarritoCompraRepository;
import sinchatgpt.nosoy.nada.pizzaHut.repository.ICrudGenericRepository;
import sinchatgpt.nosoy.nada.pizzaHut.service.ICarritoCompraService;

@RequiredArgsConstructor
@Service
public class CarritoCompraServiceImp extends CrudGenericServiceImp<CarritoCompra,Long> implements ICarritoCompraService {

    private final CarritoCompraRepository carritoCompraRepository;

    @Override
    protected ICrudGenericRepository<CarritoCompra, Long> getRepo() {
        return carritoCompraRepository;
    }
}
