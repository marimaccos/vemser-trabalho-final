package br.com.dbc.javamosdecolar.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UpdatePassagemDTO {
    @NotNull(message = "O campo dataPartida não pode estar nulo!")
    private LocalDateTime dataPartida;
    @NotNull(message = "O campo dataChegada não pode estar nulo!")
    private LocalDateTime dataChegada;
    @NotNull(message = "O campo valor não pode estar nulo!")
    private BigDecimal valor;
    @NotNull(message = "O campo idTrecho não pode estar nulo!")
    private Integer idTrecho;
}
