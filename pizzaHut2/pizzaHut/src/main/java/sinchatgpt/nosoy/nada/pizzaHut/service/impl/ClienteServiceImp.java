package sinchatgpt.nosoy.nada.pizzaHut.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sinchatgpt.nosoy.nada.pizzaHut.dto.ModeloDataAutocomplet;
import sinchatgpt.nosoy.nada.pizzaHut.model.Cliente;
import sinchatgpt.nosoy.nada.pizzaHut.repository.ClienteRepository;
import sinchatgpt.nosoy.nada.pizzaHut.repository.ICrudGenericRepository;
import sinchatgpt.nosoy.nada.pizzaHut.service.IClienteService;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service

public class ClienteServiceImp extends CrudGenericServiceImp<Cliente,String> implements IClienteService {

    Logger logger= LoggerFactory.getLogger(ClienteServiceImp.class);
    private final ClienteRepository clienteRepository;

    @Override
    protected ICrudGenericRepository<Cliente, String> getRepo() {
        return clienteRepository;
    }

    @Override
    public List<ModeloDataAutocomplet> listAutoCompletCliente() {
        List<ModeloDataAutocomplet> listarclientes = new ArrayList<>();
        try {
            for (Cliente cliente : clienteRepository.findAll()) {
                ModeloDataAutocomplet data = new ModeloDataAutocomplet();
                data.setIdx(cliente.getDniruc());
                data.setNameDysplay(cliente.getNombres());
                data.setOtherData(cliente.getTipoDocumento().name());
                listarclientes.add(data);
            }
        } catch (Exception e) {
            logger.error("Error durante la operación", e);
        }
        return listarclientes;
    }

}
