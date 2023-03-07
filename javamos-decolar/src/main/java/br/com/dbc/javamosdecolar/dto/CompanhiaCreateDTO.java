package br.com.dbc.javamosdecolar.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanhiaCreateDTO {

    @NotBlank
    @CNPJ
    private String cnpj;

    @NotBlank
    @Email
    private String login;

    @NotBlank
    @Size(min=3, max=20)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @NotBlank
    @Size(min=3, max=50)
    private String nome;

    @NotBlank
    @Size(min=3, max=50)
    private String nomeFantasia;
}
