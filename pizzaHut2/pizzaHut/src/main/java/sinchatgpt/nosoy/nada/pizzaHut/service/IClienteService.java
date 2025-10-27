package sinchatgpt.nosoy.nada.pizzaHut.service;

import pe.edu.upeu.sysventas.dto.ModeloDataAutocomplet;
import pe.edu.upeu.sysventas.model.Cliente;

import java.util.List;

public interface IClienteService extends ICrudGenericService<Cliente,String> {

    List<ModeloDataAutocomplet> listAutoCompletCliente();

}
