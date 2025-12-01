package pe.edu.upeu.conceptos_poo.ventapizzas.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.Usuario;

public interface IUsuarioRepository extends ICrudGenericoRepository <Usuario, Long>{
    @Query(value = "SELECT u.* FROM ss_usuario u WHERE u.nombre_user=:userx ",
            nativeQuery = true)
    Usuario buscarUsuario(@Param("userx") String userx);
    @Query(value = "SELECT u.* FROM ss_usuario u WHERE u.nombre_user=:user and u.clave=:clave", nativeQuery = true)
    Usuario loginUsuario(@Param("user") String user, @Param("clave") String
            clave);
    @Query("SELECT u FROM Usuario u WHERE u.nombre_Usuario = :nombreUsuario") // Consulta JPQL explícita
    Usuario findByNombre_Usuario(@Param("nombreUsuario") String nombreUsuario);
}
