package sinchatgpt.nosoy.nada.pizzaHut.service;

import sinchatgpt.nosoy.nada.pizzaHut.dto.ModeloDataAutocomplet;
import sinchatgpt.nosoy.nada.pizzaHut.model.Cliente;

import java.util.List;

public interface IClienteService extends ICrudGenericService<Cliente,String> {

    List<ModeloDataAutocomplet> listAutoCompletCliente();

}
