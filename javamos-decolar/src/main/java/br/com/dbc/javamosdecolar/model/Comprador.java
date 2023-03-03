package br.com.dbc.javamosdecolar.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class Comprador extends Usuario {

    private Integer idComprador;
    @NotNull
    @NotBlank
    @Size(max = 11)
    private String cpf;

    public Comprador(Integer idUsuario, String login, String senha, String nome,
                     TipoUsuario tipoUsuario, String cpf, Integer idComprador) {
        super(idUsuario, login, senha, nome, tipoUsuario);
        this.cpf = cpf;
        this.idComprador = idComprador;
    }

    public Comprador(Integer idUsuario, String login, String senha, String nome, TipoUsuario tipoUsuario,
                     String cpf) {
        super(idUsuario, login, senha, nome, tipoUsuario);
        this.cpf = cpf;
    }
}
