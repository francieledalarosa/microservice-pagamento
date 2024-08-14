package estudomicroservice.cartoes.aplication.infra;

import estudomicroservice.cartoes.domain.ClienteCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteCartaoRepository extends JpaRepository<ClienteCartaoRepository, Long> {
    List<ClienteCartao> findByCpf(String cpf);
}
