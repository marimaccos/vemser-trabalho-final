package br.com.dbc.javamosdecolar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassagemDTO extends PassagemCreateDTO {
    @Schema(description = "id da passagem", example = "true")
    private int idPassagem;
    @Schema(description = "codigo de identificacao da passagem", example = "81318a4b-491b-4b2e-8df4-4241fb8bcf42")
    private String codigo;
    @Schema(description = "disponibilidade de compra da passagem", example = "true")
    private boolean disponivel;
}
