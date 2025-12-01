package sinchatgpt.nosoy.nada.pizzaHut.service;

import org.springframework.stereotype.Service;
import sinchatgpt.nosoy.nada.pizzaHut.dto.ComboBoxOption;
import sinchatgpt.nosoy.nada.pizzaHut.model.Categoria;

import java.util.List;

@Service
public interface ICategoriaService extends ICrudGenericService<Categoria,Long> {

    List<ComboBoxOption> listarCombobox();

}
