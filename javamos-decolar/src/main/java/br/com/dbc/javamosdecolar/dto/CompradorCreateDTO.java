package br.com.dbc.javamosdecolar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CompradorCreateDTO {

    @NotNull
    @NotBlank
    @CPF
    @Schema(description = "CPF do comprador", example = "123.456.789.10", required = true)
    private String cpf;

    @NotBlank
    @Email
    @Schema(description = "Login do comprador", example = "bruno.rodrigues@email.com", required = true)
    private String login;

    @NotBlank
    @Size(min=3, max=20)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Senha do comprador", example = "123456", required = true)
    private String senha;

    @NotBlank
    @Size(min=3, max=50)
    @Schema(description = "Nome do comprador", example = "Bruno Rodrigues", required = true)
    private String nome;
}
