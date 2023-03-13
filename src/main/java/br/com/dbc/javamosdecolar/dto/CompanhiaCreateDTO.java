package br.com.dbc.javamosdecolar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "CNPJ da companhia", example = "29.406.616/0001-49", required = true)
    private String cnpj;

    @NotBlank
    @Email
    @Schema(description = "Login de acesso", example = "companhia.aviao@email.com", required = true)
    private String login;

    @NotBlank
    @Size(min=3, max=20)
    @Schema(description = "Senha de acesso", example = "123456", required = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @NotBlank
    @Size(min=3, max=50)
    @Schema(description = "Razão social da companhia", example = "Companhia aérea ltda", required = true)
    private String nome;

    @NotBlank
    @Size(min=3, max=50)
    @Schema(description = "Nome fantasia da companhia", example = "Pássaro linhas aéreas", required = true)
    private String nomeFantasia;

}
