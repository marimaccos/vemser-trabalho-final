package br.com.dbc.javamosdecolar.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UpdatePassagemDTO {
    private LocalDateTime dataPartida;
    private LocalDateTime dataChegada;
    private BigDecimal valor;
    private Integer idTrecho;
}
