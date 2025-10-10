package pe.edu.upeu.sysventas.repository;

import org.springframework.stereotype.Repository;
import pe.edu.upeu.sysventas.model.Usuario;


@Repository
public interface UsuarioRepository extends ICrudGenericRepository<Usuario,Long>{
}
