package pe.edu.upeu.sysventas.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sysventas.model.CarritoVenta;
import pe.edu.upeu.sysventas.repository.ICrudGenericRepository;
import pe.edu.upeu.sysventas.repository.VentaCarritoRepository;
import pe.edu.upeu.sysventas.service.IVentCarritoService;

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
