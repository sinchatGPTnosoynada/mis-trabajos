package sinchatgpt.nosoy.nada.pizzaHut.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sinchatgpt.nosoy.nada.pizzaHut.model.Usuario;
import sinchatgpt.nosoy.nada.pizzaHut.repository.ICrudGenericRepository;
import sinchatgpt.nosoy.nada.pizzaHut.repository.UsuarioRepository;
import sinchatgpt.nosoy.nada.pizzaHut.service.IUsuarioService;
@RequiredArgsConstructor
@Service
public class UsuarioServiceImp extends CrudGenericServiceImp<Usuario, Long> implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;
    @Override
    protected ICrudGenericRepository<Usuario, Long> getRepo() {
        return usuarioRepository;
    }

    @Override
    public Usuario loginUsuario(String user, String clave) {
        return usuarioRepository.loginUsuario(user, clave);
    }


}
