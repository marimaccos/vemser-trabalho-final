package br.com.dbc.javamosdecolar.model.dto;

import br.com.dbc.javamosdecolar.utils.CustomRegex;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
public class UpdatePassagemDTO {
    @Pattern(message = "O campo dataChegada deve ser no formato dd/MM/yyyy hh:mm!",
            regexp = CustomRegex.DATE_REGEX_PATTERN)
    @FutureOrPresent(message = "O campo dataPartida deve ser atual ou futuro!")
    private String dataPartida;
    @Pattern(message = "O campo dataChegada deve ser no formato dd/MM/yyyy hh:mm!",
            regexp = CustomRegex.DATE_REGEX_PATTERN)
    @FutureOrPresent(message = "O campo dataChegada deve ser atual ou futuro!")
    private String dataChegada;
    private BigDecimal valor;
    private Integer idTrecho;
}
