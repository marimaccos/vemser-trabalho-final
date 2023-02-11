package javamos_decolar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Comprador extends Usuario implements Historico {
    private String cpf;
    private List<Venda> historicoCompras = new ArrayList<>();

    public Comprador(String login, String senha, String nome, Tipo tipo) {
        super(login, senha, nome, tipo);
    }

    public Comprador(String login, String senha, String nome, Tipo tipo, String cpf) {
        super(login, senha, nome, tipo);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Venda> getHistoricoCompras() {
        return historicoCompras;
    }

    public void setHistoricoCompras(List<Venda> historicoCompras) {
        this.historicoCompras = historicoCompras;
    }

    @Override
    public void imprimirHistorico() {
        if(getHistoricoCompras().isEmpty()) {
            System.out.println("Nenhuma compra encontrada.");
        } else {
            historicoCompras.stream()
                    .forEach(System.out::println);
        }

    }

    public void comprarPassagem(String codigoPassagem, PassagemDados passagemDados, VendaDados vendaDados) {
        Optional<Passagem> passagemOptional = passagemDados.pegarPassagemPorCodigo(codigoPassagem);
        if(passagemOptional.isEmpty()) {
            System.err.println("Passagem não pode ser encontrada");
        } else {
            Passagem passagem = passagemOptional.get();
            Venda venda = new Venda();
            venda.efetuarVenda(passagem, this, passagem.getTrecho().getCompanhia(), vendaDados);
            System.out.println("Compra efetuada com sucesso!");
        }
    }


    public void pesquisarPassagem(PassagemDados passagemDados, Trecho trecho) {
        List<Passagem> passagensEncontradas = passagemDados.getListaDePassagens().stream()
                .filter(passagem -> passagem.getTrecho().equals(trecho) && passagem.isDisponivel())
                .toList();

        passagensEncontradas.stream()
                .forEach(System.out::println);
    }
}
