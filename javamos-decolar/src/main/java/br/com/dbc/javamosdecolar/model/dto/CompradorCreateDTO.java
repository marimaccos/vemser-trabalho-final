package br.com.dbc.javamosdecolar.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @NotBlank
    @Size(min=3, max=20)
    private String login;

    @NotBlank
    @Size(min=3, max=20)
    @JsonIgnoreProperties(allowSetters = true)
    private String senha;

    @NotBlank
    @Size(min=3, max=50)
    private String nome;
}
