package br.com.dbc.javamosdecolar.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanhiaCreateDTO {

    @NotBlank
    @Size(min=14, max=14)
    private String cnpj;

    @NotBlank
    @Size(min=3, max=20)
    private String login;

    @NotBlank
    @Size(min=3, max=20)
    @JsonIgnoreProperties(allowSetters = true)
    private String senha;

    @NotBlank
    @Size(min=3, max=50)
    private String nomeFantasia;
}
