package br.com.dbc.javamosdecolar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trecho {
    private int idTrecho;
    private String origem;
    private String destino;
    private Companhia companhia;
}
