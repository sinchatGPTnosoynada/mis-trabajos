package sinchatgpt.nosoy.nada.pizzaHut.service;

import sinchatgpt.nosoy.nada.pizzaHut.model.Usuario;

public interface IUsuarioService extends ICrudGenericService<Usuario,Long>{

    Usuario loginUsuario(String user, String clave);

}
