package br.com.dbc.javamosdecolar.model.dto;

import br.com.dbc.javamosdecolar.utils.CustomRegex;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
public class CreatePassagemDTO {
    @NotNull(message = "O campo dataPartida não pode estar nulo!")
    @Pattern(message = "O campo dataPartida deve ser no formato dd/MM/yyyy hh:mm!",
            regexp = CustomRegex.DATE_REGEX_PATTERN)
    private String dataPartida;

    @NotNull(message = "O campo dataChegada não pode estar nulo!")
    @Pattern(message = "O campo dataChegada deve ser no formato dd/MM/yyyy hh:mm!",
            regexp = CustomRegex.DATE_REGEX_PATTERN)
    private String dataChegada;

    @NotNull(message = "O campo valor não pode estar nulo!")
    private BigDecimal valor;

    @NotNull(message = "O campo idTrecho não pode estar nulo!")
    private Integer idTrecho;
}
