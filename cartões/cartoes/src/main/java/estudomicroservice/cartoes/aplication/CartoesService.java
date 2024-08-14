package estudomicroservice.cartoes.aplication;

import estudomicroservice.cartoes.aplication.infra.CartoesRepository;
import estudomicroservice.cartoes.domain.Cartao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartoesService {

    private final CartoesRepository cartoesRepository;

    @Transactional
    public Cartao salvar(Cartao cartao) {
        return cartoesRepository.save(cartao);
    }

    public List<Cartao> getCartoesRendaMenorIgual(Long renda) {
       var rendaBigDecimal = BigDecimal.valueOf(renda);
        return  cartoesRepository.findByRendaLessThanEqual(rendaBigDecimal);
    }
}
