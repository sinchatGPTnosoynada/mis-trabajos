package pe.edu.upeu.conceptos_poo.ventapizzas.service;

import pe.edu.upeu.conceptos_poo.ventapizzas.dto.ComboBoxOption;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.UnidadMedida;

import java.util.List;

public interface UnidadMedidaService extends CRUD_GenericoSefvice_Interface<UnidadMedida, Long>{
    List<ComboBoxOption> listarCombobox();
}
