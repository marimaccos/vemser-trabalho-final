package javamos_decolar.com.javamosdecolar.model;

import java.util.ArrayList;
import java.util.List;


public class Companhia extends Usuario{

    private String cnpj;
    private String nomeFantasia;
    private List<Venda> historicoVendas;
    private List<Passagem> passagensCadastradas;
    private List<Trecho> trechosCadastrados;


    public Companhia(String login, String senha, String nome, TipoUsuario tipoUsuario) {
        super(login, senha, nome, tipoUsuario);
        this.historicoVendas = new ArrayList<>();
        this.passagensCadastradas = new ArrayList<>();
        this.trechosCadastrados = new ArrayList<>();
    }

    public Companhia(String login, String senha, String nome, TipoUsuario tipoUsuario, String cnpj) {
        super(login, senha, nome, tipoUsuario);
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
                "cnpj='" + cnpj + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", historicoVendas=" + historicoVendas +
                ", passagensCadastradas=" + passagensCadastradas +
                ", trechosCadastrados=" + trechosCadastrados +
                '}';
    }
}
