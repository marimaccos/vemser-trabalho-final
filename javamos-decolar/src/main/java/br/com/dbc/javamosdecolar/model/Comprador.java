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
}
