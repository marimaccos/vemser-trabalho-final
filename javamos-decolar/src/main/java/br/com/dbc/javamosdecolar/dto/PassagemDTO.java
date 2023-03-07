package br.com.dbc.javamosdecolar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassagemDTO extends CreatePassagemDTO {
    private int idPassagem;
    private String codigo;
    private boolean disponivel;
}
