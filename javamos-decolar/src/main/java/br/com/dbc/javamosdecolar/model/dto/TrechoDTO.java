package br.com.dbc.javamosdecolar.model.dto;

import br.com.dbc.javamosdecolar.model.Companhia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrechoDTO extends TrechoCreateDTO{
    private int idTrecho;
    private Companhia companhia;
}
