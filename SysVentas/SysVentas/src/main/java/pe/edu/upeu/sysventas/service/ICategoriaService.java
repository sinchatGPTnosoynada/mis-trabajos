package pe.edu.upeu.sysventas.service;

import org.springframework.stereotype.Service;
import pe.edu.upeu.sysventas.dto.ComboBoxOption;
import pe.edu.upeu.sysventas.model.Categoria;

import java.util.List;

@Service
public interface ICategoriaService extends ICrudGenericService<Categoria,Long> {

    List<ComboBoxOption> listarCombobox();

}
