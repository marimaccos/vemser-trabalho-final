package javamos_decolar;

import java.util.ArrayList;
import java.util.List;

public class Comprador extends Usuario implements Historico {
    private String cpf;
    private List<Venda> historicoCompras = new ArrayList<Venda>;

    public Comprador(String login, String senha, String nome, Tipo tipo) {
        super(login, senha, nome, tipo);
    }

    @Override
    public void imprimirHistorico() {

    }

    public boolean comprarPassagem(Passagem passagem) {
        return false;
    }

    public List<Passagem> pesquisarPassagem() {

    }
}
