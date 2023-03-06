package br.com.dbc.javamosdecolar.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanhiaDTO extends CompanhiaCreateDTO{
    private Integer idCompanhia;
}
