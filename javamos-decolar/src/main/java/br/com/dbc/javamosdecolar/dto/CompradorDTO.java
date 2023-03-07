package br.com.dbc.javamosdecolar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompradorDTO extends CompradorCreateDTO {

    private Integer idComprador;
}
