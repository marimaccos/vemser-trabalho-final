package br.com.dbc.javamosdecolar.model.dto;

import br.com.dbc.javamosdecolar.utils.CustomRegex;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
public class PassagemDTO extends CreatePassagemDTO {
    private int idPassagem;
    private String codigo;
}
