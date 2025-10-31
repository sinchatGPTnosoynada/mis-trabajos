package sinchatgpt.nosoy.nada.pizzaHut.service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import sinchatgpt.nosoy.nada.pizzaHut.model.Venta;

import java.io.File;
import java.sql.SQLException;

public interface IVentaService extends ICrudGenericService<Venta,Long>{

    File getFile(String filex);
    JasperPrint runReport(Long idv) throws JRException, SQLException;


}
