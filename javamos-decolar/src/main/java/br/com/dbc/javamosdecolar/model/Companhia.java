package br.com.dbc.javamosdecolar.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public final class Companhia extends Usuario {

    private Integer idCompanhia;
    @NotBlank
    @Size(min=14, max=14)
    private String cnpj;

    @NotBlank
    @Size(min=3, max=40)
    private String nomeFantasia;

    public Companhia() {

    }
    public Companhia(Integer idUsuario, String login, String senha, String nome,
                     TipoUsuario tipoUsuario, String cnpj, Integer idComprador, String nomeFantasia) {
        super(idUsuario, login, senha, nome, tipoUsuario);
        this.idCompanhia = idComprador;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
    }

    public Companhia(Integer idUsuario, String login, String senha, String nome, TipoUsuario tipoUsuario, String cnpj,
                     String nomeFantasia) {
        super(idUsuario, login, senha, nome, tipoUsuario);
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
    }

    @Override
    public String toString() {
        return "Companhia{" +
                ", idCompanhia=" + idCompanhia +
                ", idUsuario='" + this.getIdUsuario() + '\'' +
                ", login='" + this.getLogin() + '\'' +
                ", nome='" + this.getNome() + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                '}';
    }
}

