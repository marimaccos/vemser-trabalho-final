package br.com.dbc.javamosdecolar.docs;

import br.com.dbc.javamosdecolar.dto.TrechoCreateDTO;
import br.com.dbc.javamosdecolar.dto.TrechoDTO;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Trecho", description = "Endpoints de trecho")
public interface TrechoDoc {

    @Operation(summary = "Cria um trecho", description = "Cadastra um novo trecho")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna a lista solicitada"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<TrechoDTO> create(@Valid @RequestBody TrechoCreateDTO trecho) throws RegraDeNegocioException;

    @Operation(summary = "Atualiza um trecho", description = "Atualiza um trecho cadastrado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista solicitada"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idTrecho}")
    ResponseEntity<TrechoDTO> update(@PathVariable("idTrecho") Integer idTrecho,
                                     @Valid @RequestBody TrechoCreateDTO trecho) throws RegraDeNegocioException;

    @Operation(summary = "Deleta um trecho", description = "Remove o trecho da base de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idTrecho}")
    ResponseEntity<TrechoDTO> delete(@PathVariable("idTrecho") Integer idTrecho) throws RegraDeNegocioException;

    @Operation(summary = "Lista todos os trechos", description = "Lista todos os trechos cadastrados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os trechos cadastrados"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<TrechoDTO>> getAll() throws RegraDeNegocioException;

    @Operation(summary = "Retorna um trecho específico", description = "Retorna um trecho a partir de seu id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista solicitada"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idTrecho}")
    ResponseEntity<TrechoDTO> getById(@PathVariable("idTrecho") Integer idTrecho)
            throws RegraDeNegocioException;

    @Operation(summary = "Retorna trechos de uma companhia", description = "Lista todos os trechos cadastrados" +
            " pertencentes a uma companhia")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista solicitada"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCompanhia}")
    ResponseEntity<List<TrechoDTO>> getByCompanhia(@PathVariable("idCompanhia") Integer idCompanhia)
            throws RegraDeNegocioException;
}
