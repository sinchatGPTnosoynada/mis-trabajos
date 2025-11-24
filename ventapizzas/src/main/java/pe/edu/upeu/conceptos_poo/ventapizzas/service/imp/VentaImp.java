package pe.edu.upeu.conceptos_poo.ventapizzas.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.Venta;
import pe.edu.upeu.conceptos_poo.ventapizzas.repository.ICrudGenericoRepository;
import pe.edu.upeu.conceptos_poo.ventapizzas.repository.IVentaRepository;
import pe.edu.upeu.conceptos_poo.ventapizzas.service.VentaService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaImp extends CRUD_GenericoServiceImp<Venta, Long> implements VentaService {

    private final IVentaRepository ventaRepository;

    @Override
    protected ICrudGenericoRepository<Venta, Long> getRepository() {
        return ventaRepository;
    }

    @Override
    @Transactional
    public Venta save(Venta venta) {
        return super.save(venta);
    }

    @Override
    public List<Venta> buscarPorRangoFecha(LocalDateTime inicio, LocalDateTime fin) {
        return ventaRepository.findByFechaVentaBetween(inicio, fin);
    }
}