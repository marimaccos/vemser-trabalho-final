package br.com.dbc.javamosdecolar.model.dto;

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
public class CreatePassagemDTO {
    @NotNull(message = "O campo dataPartida não pode estar nulo!")
    @FutureOrPresent(message = "O campo dataPartida deve ser atual ou futuro!")
    private LocalDateTime dataPartida;

    @NotNull(message = "O campo dataChegada não pode estar nulo!")
    @FutureOrPresent(message = "O campo dataChegada deve ser atual ou futuro!")
    private LocalDateTime dataChegada;

    @NotNull(message = "O campo valor não pode estar nulo!")
    private BigDecimal valor;

    @NotNull(message = "O campo idTrecho não pode estar nulo!")
    private Integer idTrecho;
}
