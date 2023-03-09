package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.dto.CompradorCreateDTO;
import br.com.dbc.javamosdecolar.dto.CompradorDTO;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface CompradorDoc {
    @Operation(summary = "Listar compradores", description = "Lista todos os compradores cadastrados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de compradores cadastrados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<CompradorDTO>> list() throws RegraDeNegocioException;

    @Operation(summary = "Buscar comprador por id", description = "Mostra os dados do comprador pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o comprador solicitado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idComprador}")
    public ResponseEntity<CompradorDTO> getCompradorByID(@PathVariable("idComprador") Integer idComprador)
            throws RegraDeNegocioException;

    @Operation(summary = "Criar comprador", description = "Cria um novo comprador")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o comprador criado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<CompradorDTO> create(@Valid @RequestBody CompradorCreateDTO comprador)
            throws RegraDeNegocioException;

    @Operation(summary = "Editar comprador por id", description = "Edita os dados do comprador pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os novos dados do comprador"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idComprador}")
    public ResponseEntity<CompradorDTO> update(@PathVariable("idComprador") Integer idComprador,
                                               @Valid @RequestBody CompradorCreateDTO comprador)
            throws RegraDeNegocioException;

    @Operation(summary = "Deletar comprador por id", description = "Deleta o comprador selecionado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna que a operação foi realizada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idComprador}")
    public ResponseEntity<Void> delete(@PathVariable("idComprador") Integer idComprador) throws RegraDeNegocioException;
}
