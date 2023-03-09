package br.com.dbc.javamosdecolar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaCreateDTO {
    @Schema(description = "id do comprador", example = "3", required = true)
    @NotNull(message = "O campo idComprador não pode estar nulo!")
    private Integer idComprador;

    @Schema(description = "id da companhia que oferta a passagem", example = "6", required = true)
    @NotNull(message = "O campo idCompanhia não pode estar nulo!")
    private Integer idCompanhia;

    @Schema(description = "id da passagem a ser comprada", example = "2", required = true)
    @NotNull(message = "O campo idPassagem não pode estar nulo!")
    private Integer idPassagem;
}
