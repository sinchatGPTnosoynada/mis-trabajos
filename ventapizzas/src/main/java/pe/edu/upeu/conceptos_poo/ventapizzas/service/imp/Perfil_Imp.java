package pe.edu.upeu.conceptos_poo.ventapizzas.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.conceptos_poo.ventapizzas.modelos.Perfil;
import pe.edu.upeu.conceptos_poo.ventapizzas.repository.ICrudGenericoRepository;
import pe.edu.upeu.conceptos_poo.ventapizzas.repository.IPerfilRepository;
import pe.edu.upeu.conceptos_poo.ventapizzas.service.PerfilService;

@RequiredArgsConstructor
@Service
public class Perfil_Imp extends CRUD_GenericoServiceImp<Perfil, Long> implements PerfilService {

    private final IPerfilRepository perfilRepository;

    @Override
    protected ICrudGenericoRepository<Perfil, Long> getRepository() {
        return perfilRepository;
    }
}
