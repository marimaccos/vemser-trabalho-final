package br.com.dbc.javamosdecolar.dto;

import br.com.dbc.javamosdecolar.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaDTO extends VendaCreateDTO {
    private Integer idVenda;
    private String codigo;
    private Status status;
    private LocalDateTime data;
}
