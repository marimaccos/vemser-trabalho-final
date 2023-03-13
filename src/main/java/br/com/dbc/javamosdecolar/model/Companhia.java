package br.com.dbc.javamosdecolar.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class Companhia extends Usuario {

    private Integer idCompanhia;
    private String cnpj;
    private String nomeFantasia;

    public Companhia(Integer idUsuario, String login, String senha, String nome,
                     TipoUsuario tipoUsuario, boolean ativo,
                     Integer idCompanhia, String cnpj, String nomeFantasia) {
        super(idUsuario, login, senha, nome, tipoUsuario, ativo);
        this.idCompanhia = idCompanhia;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
    }

    public Companhia(Integer idUsuario, String login, String senha, String nome,
                     TipoUsuario tipoUsuario, boolean ativo, String cnpj, String nomeFantasia) {
        super(idUsuario, login, senha, nome, tipoUsuario, ativo);
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
    }
}

