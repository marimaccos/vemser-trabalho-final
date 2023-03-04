package br.com.dbc.javamosdecolar.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CompradorCreateDTO {

    @NotNull
    @NotBlank
    @Size(max = 11)
    private String cpf;
}
