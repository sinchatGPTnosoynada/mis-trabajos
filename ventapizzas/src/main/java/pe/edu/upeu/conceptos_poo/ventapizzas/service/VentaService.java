package pe.edu.upeu.conceptos_poo.ventapizzas.service;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.Venta;
import java.time.LocalDateTime;
import java.util.List;

public interface VentaService extends CRUD_GenericoSefvice_Interface<Venta, Long> {
    List<Venta> buscarPorRangoFecha(LocalDateTime inicio, LocalDateTime fin);
}