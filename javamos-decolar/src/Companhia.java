package javamos_decolar;

import java.util.ArrayList;
import java.util.List;

public class Companhia extends Usuario implements Historico {

    private String cnpj;
    private List<Venda> historicoVendas;
    private List<Passagem> passagensCadastradas;
    private List<Trecho> trechosCadastrados;


    public Companhia(String login, String senha, String nome, Tipo tipo) {
        super(login, senha, nome, tipo);
        this.historicoVendas = new ArrayList<>();
        this.passagensCadastradas = new ArrayList<>();
        this.trechosCadastrados = new ArrayList<>();
    }

    public Companhia(String login, String senha, String nome, Tipo tipo, String cnpj) {
        super(login, senha, nome, tipo);
        this.cnpj = cnpj;
        this.historicoVendas = new ArrayList<>();
        this.passagensCadastradas = new ArrayList<>();
        this.trechosCadastrados = new ArrayList<>();
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

    public boolean criarTrecho(Trecho trechoDesejado, TrechoDados trechoDados) {

        boolean oTrechoExiste = trechoDados.checaSeOTrechoExiste(trechoDesejado.getDestino(),
                trechoDesejado.getOrigem(), this);

        if(!oTrechoExiste) {
            trechoDados.adicionar(trechoDesejado);
            this.getTrechosCadastrados().add(trechoDesejado);
            return true;
        }

        System.err.println("Trecho já cadastrado!");
        return false;
    }

    public boolean editarTrecho(Integer index, Trecho trechoDesejado, TrechoDados trechoDados) {

        boolean oTrechoExiste = trechoDados.checaSeOTrechoExiste(trechoDesejado.getDestino(),
                trechoDesejado.getOrigem(), this);

        if(!oTrechoExiste) {
            trechoDados.editar(index, trechoDesejado);
            return true;
        }

        System.err.println("Trecho já cadastrado!");
        return false;
    }

    public boolean deletarTrecho(Integer index, TrechoDados trechoDados) {

        if(index > this.getTrechosCadastrados().size()) {
            System.err.println("Index não existente.");
            return false;
        }

        Trecho trecho = this.getTrechosCadastrados().get(index);
        System.out.println(trecho);
        Integer indexNoTrechoDados = trechoDados.getListaDeTrechos().indexOf(trecho);

        if(indexNoTrechoDados != null) {
            // remove da lista de trechos cadastrados da companhia
            this.getTrechosCadastrados().remove(index.intValue());
            // remove do "banco de dados" de trechos
            trechoDados.remover(indexNoTrechoDados);
            return true;
        }

        System.err.println("Trecho não cadastrado!");
        return false;
    }

    public boolean deletarPassagem(Integer index, PassagemDados passagemDados) {

        if(index > this.getPassagensCadastradas().size()) {
            System.err.println("Index não existente.");
            return false;
        }

        Passagem passagem = this.getPassagensCadastradas().get(index);
        System.out.println(passagem);
        Integer indexNoPassagemDados = passagemDados.getListaDePassagens().indexOf(passagem);

        if(indexNoPassagemDados != null) {
            // remove da lista de passagens cadastrados da companhia
            this.getPassagensCadastradas().remove(index.intValue());
            // remove do "banco de dados" de passagens
            passagemDados.remover(indexNoPassagemDados);
            return true;
        }
        System.err.println("Passagem não cadastrada!");
        return false;
    }

    @Override
    public String toString() {
        return "Companhia{" +
                "cnpj='" + cnpj + '\'' +
                ", historicoVendas=" + historicoVendas +
                ", passagensCadastradas=" + passagensCadastradas +
                ", trechosCadastrados=" + trechosCadastrados +
                '}';
    }
}
