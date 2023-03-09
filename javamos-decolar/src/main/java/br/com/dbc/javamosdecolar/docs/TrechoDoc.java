package br.com.dbc.javamosdecolar.docs;

import br.com.dbc.javamosdecolar.dto.TrechoCreateDTO;
import br.com.dbc.javamosdecolar.dto.TrechoDTO;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface TrechoDoc {


    @Operation(summary = "Listar ultimas passagens", description = "Lista as ultimas passagens cadastradas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista solicitada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<TrechoDTO>> list() throws RegraDeNegocioException;

    @Operation(summary = "Listar ultimas passagens", description = "Lista as ultimas passagens cadastradas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista solicitada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idTrecho}")
    public ResponseEntity<TrechoDTO> getTrechoById(@PathVariable("idTrecho") Integer idTrecho)
            throws RegraDeNegocioException;

    @Operation(summary = "Listar ultimas passagens", description = "Lista as ultimas passagens cadastradas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista solicitada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCompanhia}")
    public ResponseEntity<List<TrechoDTO>> getTrechosPorCompanhia(@PathVariable("idCompanhia") Integer idCompanhia)
            throws RegraDeNegocioException;

    @Operation(summary = "Listar ultimas passagens", description = "Lista as ultimas passagens cadastradas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista solicitada"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<TrechoDTO> create(@Valid @RequestBody TrechoCreateDTO trecho) throws RegraDeNegocioException;

    @Operation(summary = "Listar ultimas passagens", description = "Lista as ultimas passagens cadastradas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista solicitada"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idTrecho}")
    public ResponseEntity<TrechoDTO> update(@PathVariable("idTrecho") Integer idTrecho,
                                            @Valid @RequestBody TrechoCreateDTO trecho) throws RegraDeNegocioException;

    @Operation(summary = "Deleta o trecho", description = "Remove o trecho da base de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idTrecho}")
    public ResponseEntity<TrechoDTO> delete(@PathVariable("idTrecho") Integer idTrecho) throws RegraDeNegocioException;
}