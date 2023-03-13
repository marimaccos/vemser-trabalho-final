package br.com.dbc.javamosdecolar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    private Integer idUsuario;
    private String login;
    private String senha;
    private String nome;
    private TipoUsuario tipoUsuario;
    private boolean ativo;

    public Usuario(String login, String senha, String nome, TipoUsuario tipoUsuario, boolean ativo) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.tipoUsuario = tipoUsuario;
        this.ativo = ativo;
    }
}