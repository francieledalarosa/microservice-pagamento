package estudomicroservice.avaliadorcredito.application;

import estudomicroservice.avaliadorcredito.application.exception.DadosClientNoutFoundException;
import estudomicroservice.avaliadorcredito.application.exception.ErroComunicacaoMicroservicesException;
import estudomicroservice.avaliadorcredito.application.infra.CartoesResourceClient;
import estudomicroservice.avaliadorcredito.application.infra.ClienteResourceClient;
import estudomicroservice.avaliadorcredito.domain.*;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient resourceClient;
    private final CartoesResourceClient resourceCartoes;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClientNoutFoundException ,
    ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = resourceClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = resourceCartoes.getCartoesByCliente(cpf);

            return SituacaoCliente.builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        }catch (FeignException.FeignClientException ex){
            int status = ex.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClientNoutFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(ex.getMessage(), status);
        }
    }


    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
            throws DadosClientNoutFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = resourceClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = resourceCartoes.getCartoesRendaAte(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();
            var listaCartoesAprovados = cartoes.stream().map(cartao -> {
                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);
                return aprovado;
            }).collect(Collectors.toList());
            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        } catch (FeignException.FeignClientException ex) {
            int status = ex.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClientNoutFoundException();
            } else {
                throw new ErroComunicacaoMicroservicesException(ex.getMessage(), status);
            }
        }
    }
}
