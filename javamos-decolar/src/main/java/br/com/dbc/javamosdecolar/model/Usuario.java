package br.com.dbc.javamosdecolar.model;

import br.com.dbc.javamosdecolar.model.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
@AllArgsConstructor
public class Usuario {

    private Integer idUsuario;

    @NotBlank
    @Size(min=3, max=20)
    private String login;

    @NotBlank
    @Size(min=3, max=20)
    private String senha;

    @NotBlank
    @Size(min=3, max=50)
    private String nome;

    @NotNull
    private TipoUsuario tipoUsuario;

    public Usuario(String login, String senha, String nome, TipoUsuario tipoUsuario) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario() {}

}