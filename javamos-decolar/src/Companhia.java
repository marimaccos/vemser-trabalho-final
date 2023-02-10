package javamos_decolar;

import java.util.List;

public class Companhia extends Usuario implements Historico {

    private String cnpj;
    private List<Passagem> historicoVendas;
    private List<Trecho> trechosCadastrados;

    public Companhia(String login, String senha, String nome, Tipo tipo) {
        super(login, senha, nome, tipo);
    }

    @Override
    public void imprimirHistorico() {

    }

    public Passagem cadastrarPassagem() {

    }

    public Trecho criarTrecho() {

    }
}
