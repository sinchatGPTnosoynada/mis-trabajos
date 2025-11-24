package sinchatgpt.nosoy.nada.pizzaHut.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sinchatgpt.nosoy.nada.pizzaHut.model.CarritoVenta;
import sinchatgpt.nosoy.nada.pizzaHut.repository.ICrudGenericRepository;
import sinchatgpt.nosoy.nada.pizzaHut.repository.VentaCarritoRepository;
import sinchatgpt.nosoy.nada.pizzaHut.service.IVentCarritoService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VentCarritoServiceImp extends CrudGenericServiceImp<CarritoVenta, Long> implements IVentCarritoService {

    private final VentaCarritoRepository carritoRepository;

    @Override
    protected ICrudGenericRepository<CarritoVenta, Long> getRepo() {
        return carritoRepository;
    }

    @Override
    public List<CarritoVenta> listaCarritoCliente(String dni) {
        return carritoRepository.listaCarritoCliente(dni);
    }
    @Transactional
    @Override
    public void deleteCarAll(String dniruc) {
        carritoRepository.deleteByDniruc(dniruc);
    }


}
