package sinchatgpt.nosoy.nada.pizzaHut.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sinchatgpt.nosoy.nada.pizzaHut.model.CompraDetalle;
import sinchatgpt.nosoy.nada.pizzaHut.repository.CompraDetalleRepository;
import sinchatgpt.nosoy.nada.pizzaHut.repository.ICrudGenericRepository;
import sinchatgpt.nosoy.nada.pizzaHut.service.ICompraDetalleService;

@RequiredArgsConstructor
@Service
public class CompraDetalleServiceImp extends CrudGenericServiceImp<CompraDetalle, Long> implements ICompraDetalleService {

    private final CompraDetalleRepository compraDetalleRepository;

    @Override
    protected ICrudGenericRepository<CompraDetalle, Long> getRepo() {
        return compraDetalleRepository;
    }
}
