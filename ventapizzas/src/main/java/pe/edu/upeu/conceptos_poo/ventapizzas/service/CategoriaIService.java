package pe.edu.upeu.conceptos_poo.ventapizzas.service;

import pe.edu.upeu.conceptos_poo.ventapizzas.dto.ComboBoxOption;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.Categoria;

import java.util.List;

public interface CategoriaIService extends CRUD_GenericoSefvice_Interface<Categoria, Long> {
    List<ComboBoxOption> listarCombobox();
}
