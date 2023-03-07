package br.com.dbc.javamosdecolar.model.dto;

import br.com.dbc.javamosdecolar.model.Companhia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrechoCreateDTO {
    @NotNull
    @Size(min = 3, max = 3, message = "Campo origem deve ser no formato XXX!")
    private String origem;
    @NotNull
    @Size(min = 3, max = 3, message = "Campo DESTINO deve ser no formato XXX!")
    private String destino;
    @NotNull
    private Integer idCompanhia;
}
