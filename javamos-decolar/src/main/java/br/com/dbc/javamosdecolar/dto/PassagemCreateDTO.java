package br.com.dbc.javamosdecolar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassagemCreateDTO {
    @Schema(description = "Data de partida", example = "03/03/2024", required = true)
    @NotNull(message = "O campo dataPartida não pode estar nulo!")
    @FutureOrPresent(message = "O campo dataPartida deve ser atual ou futuro!")
    private LocalDateTime dataPartida;

    @Schema(description = "Data de chegada", example = "04/03/2024", required = true)
    @NotNull(message = "O campo dataChegada não pode estar nulo!")
    @FutureOrPresent(message = "O campo dataChegada deve ser atual ou futuro!")
    private LocalDateTime dataChegada;

    @Schema(description = "Valor da passagem", example = "800", required = true)
    @NotNull(message = "O campo valor não pode estar nulo!")
    private BigDecimal valor;

    @Schema(description = "ID do trecho", example = "5", required = true)
    @NotNull(message = "O campo idTrecho não pode estar nulo!")
    private Integer idTrecho;
}
