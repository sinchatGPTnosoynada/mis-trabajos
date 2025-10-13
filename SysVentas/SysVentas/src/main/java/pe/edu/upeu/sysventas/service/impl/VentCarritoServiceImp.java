package pe.edu.upeu.sysventas.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysventas.model.CarritoVenta;
import pe.edu.upeu.sysventas.repository.ICrudGenericRepository;
import pe.edu.upeu.sysventas.repository.VentaCarritoRepository;
import pe.edu.upeu.sysventas.service.IVentCarritoService;
@RequiredArgsConstructor
@Service
public class VentCarritoServiceImp extends CrudGenericServiceImp<CarritoVenta, Long> implements IVentCarritoService {

    private final VentaCarritoRepository carritoRepository;

    @Override
    protected ICrudGenericRepository<CarritoVenta, Long> getRepo() {
        return carritoRepository;
    }
}
