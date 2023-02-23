package javamos_decolar.com.javamosdecolar.model;

import java.util.ArrayList;
import java.util.List;


public final class Companhia extends Usuario{

    private Integer idCompanhia;
    private String cnpj;
    private String nomeFantasia;
    private List<Venda> historicoVendas;
    private List<Passagem> passagensCadastradas;
    private List<Trecho> trechosCadastrados;


//    public Companhia(Integer idUsuario, String login, String senha, String nome, TipoUsuario tipoUsuario,
//                     Integer idComprador) {
//        super(idUsuario, login, senha, nome, tipoUsuario);
//        this.idComprador = idComprador;
//        this.historicoVendas = new ArrayList<>();
//        this.passagensCadastradas = new ArrayList<>();
//        this.trechosCadastrados = new ArrayList<>();
//    }

    public Companhia(Integer idUsuario, String login, String senha, String nome,
                     TipoUsuario tipoUsuario, String cnpj, Integer idComprador, String nomeFantasia) {
        super(idUsuario, login, senha, nome, tipoUsuario);
        this.idCompanhia = idComprador;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
    }

    public Integer getIdCompanhia() {
        return idCompanhia;
    }

    public void setIdCompanhia(Integer idCompanhia) {
        this.idCompanhia = idCompanhia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public List<Venda> getHistoricoVendas() {
        return historicoVendas;
    }

    public void setHistoricoVendas(List<Venda> historicoVendas) {
        this.historicoVendas = historicoVendas;
    }

    public List<Passagem> getPassagensCadastradas() {
        return passagensCadastradas;
    }

    public void setPassagensCadastradas(List<Passagem> passagensCadastradas) {
        this.passagensCadastradas = passagensCadastradas;
    }

    public List<Trecho> getTrechosCadastrados() {
        return trechosCadastrados;
    }

    public void setTrechosCadastrados(List<Trecho> trechosCadastrados) {
        this.trechosCadastrados = trechosCadastrados;
    }

    @Override
    public String toString() {
        return "Companhia{" +
                "idCompanhia=" + idCompanhia +
                ", cnpj='" + cnpj + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", historicoVendas=" + historicoVendas +
                ", passagensCadastradas=" + passagensCadastradas +
                ", trechosCadastrados=" + trechosCadastrados +
                '}';
    }
}
