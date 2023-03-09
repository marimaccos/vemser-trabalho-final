package br.com.dbc.javamosdecolar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrechoCreateDTO {
    @Schema(description = "código do aeroporto de origem", example = "BEL", required = true)
    @NotNull
    @Size(min = 3, max = 3, message = "Campo origem deve ser no formato XXX!")
    private String origem;

    @Schema(description = "código do aeroporto de destino", example = "FOR", required = true)
    @NotNull
    @Size(min = 3, max = 3, message = "Campo destino deve ser no formato XXX!")
    private String destino;

    @Schema(description = "id da companhia que oferece o trecho", example = "6", required = true)
    @NotNull
    private Integer idCompanhia;
}
