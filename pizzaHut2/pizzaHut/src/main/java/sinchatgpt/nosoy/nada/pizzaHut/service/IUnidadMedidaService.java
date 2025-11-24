package sinchatgpt.nosoy.nada.pizzaHut.service;

import sinchatgpt.nosoy.nada.pizzaHut.dto.ComboBoxOption;
import sinchatgpt.nosoy.nada.pizzaHut.model.UnidadMedida;

import java.util.List;

public interface IUnidadMedidaService extends  ICrudGenericService<UnidadMedida,Long> {

    List<ComboBoxOption> listarCombobox();


}
