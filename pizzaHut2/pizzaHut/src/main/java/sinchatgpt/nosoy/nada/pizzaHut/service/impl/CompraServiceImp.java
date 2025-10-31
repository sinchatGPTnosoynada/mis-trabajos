package sinchatgpt.nosoy.nada.pizzaHut.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sinchatgpt.nosoy.nada.pizzaHut.model.Compra;
import sinchatgpt.nosoy.nada.pizzaHut.repository.CompraRepository;
import sinchatgpt.nosoy.nada.pizzaHut.repository.ICrudGenericRepository;
import sinchatgpt.nosoy.nada.pizzaHut.service.ICompraService;

@RequiredArgsConstructor
@Service
public class CompraServiceImp extends CrudGenericServiceImp<Compra,Long> implements ICompraService {

    private final CompraRepository compraRepository;

    @Override
    protected ICrudGenericRepository<Compra, Long> getRepo() {
        return compraRepository;
    }
}
