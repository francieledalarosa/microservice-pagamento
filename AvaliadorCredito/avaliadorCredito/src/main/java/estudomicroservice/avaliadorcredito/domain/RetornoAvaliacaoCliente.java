package estudomicroservice.avaliadorcredito.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Data
public class RetornoAvaliacaoCliente {
    private List<CartaoAprovado> cartoes;
}
