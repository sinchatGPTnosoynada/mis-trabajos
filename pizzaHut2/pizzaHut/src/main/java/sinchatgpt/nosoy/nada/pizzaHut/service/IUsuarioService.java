package sinchatgpt.nosoy.nada.pizzaHut.service;

import pe.edu.upeu.sysventas.model.Usuario;

public interface IUsuarioService extends ICrudGenericService<Usuario,Long>{

    Usuario loginUsuario(String user, String clave);

}
