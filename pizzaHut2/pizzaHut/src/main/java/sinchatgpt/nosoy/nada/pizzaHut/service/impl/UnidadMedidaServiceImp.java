package sinchatgpt.nosoy.nada.pizzaHut.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sinchatgpt.nosoy.nada.pizzaHut.dto.ComboBoxOption;
import sinchatgpt.nosoy.nada.pizzaHut.model.UnidadMedida;
import sinchatgpt.nosoy.nada.pizzaHut.repository.ICrudGenericRepository;
import sinchatgpt.nosoy.nada.pizzaHut.repository.UnidadMedidaRepository;
import sinchatgpt.nosoy.nada.pizzaHut.service.IUnidadMedidaService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UnidadMedidaServiceImp extends CrudGenericServiceImp<UnidadMedida, Long> implements IUnidadMedidaService {
    private final UnidadMedidaRepository unidadMedidaRepository;
    @Override
    protected ICrudGenericRepository<UnidadMedida, Long> getRepo() {

        return unidadMedidaRepository;
    }

    @Override
    public List<ComboBoxOption> listarCombobox() {
        List<ComboBoxOption> listar=new ArrayList<>();
        ComboBoxOption cb;
        for(UnidadMedida cate : unidadMedidaRepository.findAll()) {
            cb=new ComboBoxOption();
            cb.setKey(String.valueOf(cate.getIdUnidad()));
            cb.setValue(cate.getNombreMedida());
            listar.add(cb);
        }
        return listar;
    }

}
