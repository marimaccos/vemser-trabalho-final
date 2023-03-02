package br.com.dbc.javamosdecolar.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateVendaDTO {
    @NotNull(message = "O campo idVenda não pode estar nulo!")
    private Integer idVenda;
    @NotNull(message = "O campo idComprador não pode estar nulo!")
    private Integer idComprador;
    @NotNull(message = "O campo idCompanhia não pode estar nulo!")
    private Integer idCompanhia;
    @NotNull(message = "O campo idPassagem não pode estar nulo!")
    private Integer idPassagem;
}
