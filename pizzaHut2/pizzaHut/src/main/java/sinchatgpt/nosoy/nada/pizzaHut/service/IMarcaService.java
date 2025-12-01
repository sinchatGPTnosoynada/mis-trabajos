package sinchatgpt.nosoy.nada.pizzaHut.service;

import sinchatgpt.nosoy.nada.pizzaHut.dto.ComboBoxOption;
import sinchatgpt.nosoy.nada.pizzaHut.model.Marca;

import java.util.List;

public interface IMarcaService extends ICrudGenericService<Marca,Long>{

    List<ComboBoxOption> listarCombobox();

}
