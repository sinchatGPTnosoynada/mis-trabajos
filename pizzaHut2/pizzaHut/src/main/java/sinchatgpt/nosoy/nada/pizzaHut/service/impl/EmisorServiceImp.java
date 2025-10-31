package sinchatgpt.nosoy.nada.pizzaHut.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sinchatgpt.nosoy.nada.pizzaHut.model.Emisor;
import sinchatgpt.nosoy.nada.pizzaHut.repository.EmisorRepository;
import sinchatgpt.nosoy.nada.pizzaHut.repository.ICrudGenericRepository;
import sinchatgpt.nosoy.nada.pizzaHut.service.IEmisorService;
@RequiredArgsConstructor
@Service
public class EmisorServiceImp extends CrudGenericServiceImp<Emisor, Long> implements IEmisorService {
    private final EmisorRepository emisorRepository;

    @Override
    protected ICrudGenericRepository<Emisor, Long> getRepo() {
        return null;
    }
}
