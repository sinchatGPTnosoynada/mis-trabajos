package sinchatgpt.nosoy.nada.pizzaHut.service;

import pe.edu.upeu.sysventas.dto.ComboBoxOption;
import pe.edu.upeu.sysventas.model.Marca;

import java.util.List;

public interface IMarcaService extends ICrudGenericService<Marca,Long>{

    List<ComboBoxOption> listarCombobox();

}
