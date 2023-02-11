package javamos_decolar;

import java.util.List;

public class Companhia extends Usuario implements Historico {

    private String cnpj;
    private List<Venda> historicoVendas;
    private List<Passagem> passagensCadastradas;
    private List<Trecho> trechosCadastrados;

    public Companhia(String login, String senha, String nome, Tipo tipo, String cnpj, List<Venda> historicoVendas,
                     List<Passagem> passagensCadastradas, List<Trecho> trechosCadastrados) {
        super(login, senha, nome, tipo);
        this.cnpj = cnpj;
        this.historicoVendas = historicoVendas;
        this.passagensCadastradas = passagensCadastradas;
        this.trechosCadastrados = trechosCadastrados;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<Venda> getHistoricoVendas() {
        return historicoVendas;
    }

    public void setHistoricoVendas(List<Venda> historicoVendas) {
        this.historicoVendas = historicoVendas;
    }

    public List<Trecho> getTrechosCadastrados() {
        return trechosCadastrados;
    }

    public void setTrechosCadastrados(List<Trecho> trechosCadastrados) {
        this.trechosCadastrados = trechosCadastrados;
    }

    public List<Passagem> getPassagensCadastradas() {
        return passagensCadastradas;
    }

    public void setPassagensCadastradas(List<Passagem> passagensCadastradas) {
        this.passagensCadastradas = passagensCadastradas;
    }

    @Override
    public void imprimirHistorico() {
        historicoVendas.stream().forEach(System.out::println);
    }

    public boolean cadastrarPassagem(Passagem passagem, PassagemDados passagemDados) {
        boolean passagemExiste = passagemDados.getListaDePassagens().stream().anyMatch(p -> p.getCodigo().equals(passagem.getCodigo()));

        if(!passagemExiste){
            passagemDados.adicionar(passagem);
            return true;
        }
        System.err.println("Passagem já cadastrada!");
        return false;
    }

    public boolean criarTrecho(Trecho trechoDesejado, TrechoDados trechoDados, Companhia companhia) {

        boolean oTrechoExiste = trechoDados.getListaDeTrechos().stream()
                .anyMatch(trecho -> trecho.getDestino().equals(trechoDesejado.getDestino())
                        && trecho.getOrigem().equals(trechoDesejado.getOrigem())
                        && trecho.getCompanhia().equals(companhia));

        if(!oTrechoExiste) {
            trechoDados.adicionar(trechoDesejado);
            return true;
        }

        System.err.println("Trecho já cadastrado!");
        return false;
    }
}
