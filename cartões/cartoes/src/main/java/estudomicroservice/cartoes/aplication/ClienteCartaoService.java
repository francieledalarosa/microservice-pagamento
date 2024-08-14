package estudomicroservice.cartoes.aplication;

import estudomicroservice.cartoes.aplication.infra.ClienteCartaoRepository;
import estudomicroservice.cartoes.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository clienteCartaoRepository;


    public List<ClienteCartao> listCartaoByCpf(String cpf) {
        return clienteCartaoRepository.findByCpf(cpf);
    }
}
