package pe.edu.upeu.conceptos_poo.ventapizzas.service;

import pe.edu.upeu.conceptos_poo.ventapizzas.dto.ModeloDataAutocomplet;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.Cliente;

import java.util.List;

public interface ClienteService extends CRUD_GenericoSefvice_Interface<Cliente, String> {
    List<ModeloDataAutocomplet> listAutoCompletCliente();
}
