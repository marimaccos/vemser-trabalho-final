package br.com.dbc.javamosdecolar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanhiaDTO extends CompanhiaCreateDTO{
    private Integer idCompanhia;
    private Boolean ativo;
}
