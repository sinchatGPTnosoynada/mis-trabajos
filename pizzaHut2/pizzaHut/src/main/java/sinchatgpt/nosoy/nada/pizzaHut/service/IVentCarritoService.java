package sinchatgpt.nosoy.nada.pizzaHut.service;


import pe.edu.upeu.sysventas.model.CarritoVenta;

import java.util.List;

public interface IVentCarritoService extends  ICrudGenericService<CarritoVenta,Long>{

    List<CarritoVenta> listaCarritoCliente(String dni);
    void deleteCarAll(String dniruc);
}
