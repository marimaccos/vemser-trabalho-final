package br.com.dbc.javamosdecolar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompradorDTO extends CompradorCreateDTO {
    @Schema(description = "id do comprador", example = "1")
    private Integer idComprador;
    @Schema(description = "status do usuario", example = "true")
    private Boolean ativo;
}
