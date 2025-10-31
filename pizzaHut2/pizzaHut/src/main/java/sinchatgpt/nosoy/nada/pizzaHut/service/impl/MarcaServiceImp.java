package sinchatgpt.nosoy.nada.pizzaHut.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sinchatgpt.nosoy.nada.pizzaHut.dto.ComboBoxOption;
import sinchatgpt.nosoy.nada.pizzaHut.model.Marca;
import sinchatgpt.nosoy.nada.pizzaHut.repository.ICrudGenericRepository;
import sinchatgpt.nosoy.nada.pizzaHut.repository.MarcaRepository;
import sinchatgpt.nosoy.nada.pizzaHut.service.IMarcaService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MarcaServiceImp extends CrudGenericServiceImp<Marca, Long> implements IMarcaService {
    private final MarcaRepository marcaRepository;

    @Override
    protected ICrudGenericRepository<Marca, Long> getRepo() {
        return marcaRepository;
    }

    @Override
    public List<ComboBoxOption> listarCombobox() {
        List<ComboBoxOption> listar=new ArrayList<>();
        ComboBoxOption cb;
        for(Marca cate : marcaRepository.findAll()) {
            cb=new ComboBoxOption();
            cb.setKey(String.valueOf(cate.getIdMarca()));
            cb.setValue(cate.getNombre());
            listar.add(cb);
        }
        return listar;
    }


}
