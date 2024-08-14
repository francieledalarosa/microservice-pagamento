package estudomicroservice.cartoes.aplication;

import estudomicroservice.cartoes.aplication.dto.CartaoSaveRequest;
import estudomicroservice.cartoes.aplication.dto.CartoesPorClienteResponse;
import estudomicroservice.cartoes.domain.Cartao;
import estudomicroservice.cartoes.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesController {

    private final CartoesService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String Status(){
        return "ok";
    }
    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest resquest){
        Cartao cartao = resquest.toModel();
        cartaoService.salvar(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf) {
        List<ClienteCartao> lista = clienteCartaoService.listCartaoByCpf(cpf);
        List<CartoesPorClienteResponse> resultList = lista.stream()
                .map(CartoesPorClienteResponse::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultList);
    }

}
