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

    public Passagem comprarPassagem(String codigoPassagem, PassagemDados passagemDados) {
        Optional<Passagem> passagemEncontrada = passagemDados.getListaDePassagens().stream()
                .filter(passagem -> passagem.getCodigo() == codigoPassagem)
                .findFirst();

        if (passagemEncontrada.isPresent()) {
            passagemEncontrada.get().setDisponivel(false);
        }

        return passagemEncontrada.get();
    }


    public void pesquisarPassagem(PassagemDados passagemDados, Trecho trecho) {
        List<Passagem> passagensEncontradas = passagemDados.getListaDePassagens().stream()
                .filter(passagem -> passagem.getTrecho().equals(trecho) && passagem.isDisponivel())
                .toList();

        passagensEncontradas.stream()
                .forEach(System.out::println);
    }
}
