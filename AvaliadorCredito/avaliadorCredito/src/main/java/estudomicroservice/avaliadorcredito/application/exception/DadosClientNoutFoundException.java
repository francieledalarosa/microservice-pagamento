package estudomicroservice.avaliadorcredito.application.exception;

public class DadosClientNoutFoundException extends  Exception {
    public DadosClientNoutFoundException() {
        super("Dados do cliente não encontrados para o CPF informado!");
    }
}
