package br.com.dbc.javamosdecolar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanhiaDTO extends CompanhiaCreateDTO{
    @Schema(description = "id da companhia", example = "1")
    private Integer idCompanhia;
    @Schema(description = "status do usuario", example = "true")
    private Boolean ativo;
}
