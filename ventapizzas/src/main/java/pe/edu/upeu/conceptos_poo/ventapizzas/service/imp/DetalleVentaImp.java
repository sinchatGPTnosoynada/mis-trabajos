package pe.edu.upeu.conceptos_poo.ventapizzas.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.DetalleVenta;
import pe.edu.upeu.conceptos_poo.ventapizzas.repository.ICrudGenericoRepository;
import pe.edu.upeu.conceptos_poo.ventapizzas.repository.IDetalleVentaRepository;
import pe.edu.upeu.conceptos_poo.ventapizzas.service.DetalleVentaService;

@Service
@RequiredArgsConstructor
public class DetalleVentaImp extends CRUD_GenericoServiceImp<DetalleVenta, Long> implements DetalleVentaService {

    private final IDetalleVentaRepository detalleVentaRepository;

    @Override
    protected ICrudGenericoRepository<DetalleVenta, Long> getRepository() {
        return detalleVentaRepository;
    }
}