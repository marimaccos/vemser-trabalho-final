package br.com.dbc.javamosdecolar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comprador extends Usuario {

    private Integer idComprador;
    private String cpf;

    public Comprador(Integer idUsuario, String login, String nome, String senha, TipoUsuario tipoUsuario, String cpf) {
        super(idUsuario, login, nome, senha, tipoUsuario);
        this.cpf = cpf;
    }
}
