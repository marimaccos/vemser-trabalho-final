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

    public Comprador(Integer idUsuario, String login, String senha,
                     String nome, TipoUsuario tipoUsuario, boolean ativo, String cpf) {
        super(idUsuario, login, senha, nome, tipoUsuario, ativo);
        this.cpf = cpf;
    }
}
