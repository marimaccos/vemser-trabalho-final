package br.com.dbc.javamosdecolar.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVendaDTO {
    @NotNull(message = "O campo idComprador não pode estar nulo!")
    private Integer idComprador;
    @NotNull(message = "O campo idCompanhia não pode estar nulo!")
    private Integer idCompanhia;
    @NotNull(message = "O campo idPassagem não pode estar nulo!")
    private Integer idPassagem;
}
