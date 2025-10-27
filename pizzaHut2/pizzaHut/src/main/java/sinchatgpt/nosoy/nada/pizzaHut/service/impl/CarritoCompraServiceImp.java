package sinchatgpt.nosoy.nada.pizzaHut.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysventas.model.CarritoCompra;
import pe.edu.upeu.sysventas.repository.CarritoCompraRepository;
import pe.edu.upeu.sysventas.repository.ICrudGenericRepository;
import pe.edu.upeu.sysventas.service.ICarritoCompraService;

@RequiredArgsConstructor
@Service
public class CarritoCompraServiceImp extends CrudGenericServiceImp<CarritoCompra,Long> implements ICarritoCompraService {

    private final CarritoCompraRepository carritoCompraRepository;

    @Override
    protected ICrudGenericRepository<CarritoCompra, Long> getRepo() {
        return carritoCompraRepository;
    }
}
