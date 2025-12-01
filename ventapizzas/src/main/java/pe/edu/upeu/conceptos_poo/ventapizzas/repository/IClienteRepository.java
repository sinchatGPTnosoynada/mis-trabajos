package pe.edu.upeu.conceptos_poo.ventapizzas.repository;

import org.springframework.data.jpa.repository.Query;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.Cliente;

import java.util.List;

public interface IClienteRepository extends ICrudGenericoRepository<Cliente, String> {
    @Query("SELECT c FROM Cliente c") // Consulta JPQL para obtener todos los Clientes
    List<Cliente> listAutoCompletCliente();
}
