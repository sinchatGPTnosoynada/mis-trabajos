package sinchatgpt.nosoy.nada.pizzaHut.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sinchatgpt.nosoy.nada.pizzaHut.model.Proveedor;
import sinchatgpt.nosoy.nada.pizzaHut.repository.ICrudGenericRepository;
import sinchatgpt.nosoy.nada.pizzaHut.repository.ProveedorRepository;
import sinchatgpt.nosoy.nada.pizzaHut.service.IProveedorService;

@RequiredArgsConstructor
@Service
public class ProveedorServiceImp extends CrudGenericServiceImp<Proveedor, Long> implements IProveedorService {
    private final ProveedorRepository proveedorRepository;
    @Override
    protected ICrudGenericRepository<Proveedor, Long> getRepo() {
        return proveedorRepository;
    }
}
