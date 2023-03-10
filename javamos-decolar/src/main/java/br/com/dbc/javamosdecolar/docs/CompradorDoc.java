package br.com.dbc.javamosdecolar.docs;

import br.com.dbc.javamosdecolar.dto.CompradorCreateDTO;
import br.com.dbc.javamosdecolar.dto.CompradorDTO;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Comprador", description = "Endpoints de comprador")
public interface CompradorDoc {
    @Operation(summary = "Criar comprador", description = "Cria um novo comprador")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna o comprador criado"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<CompradorDTO> create(@Valid @RequestBody CompradorCreateDTO comprador)
            throws RegraDeNegocioException;

    @Operation(summary = "Editar comprador por id", description = "Edita os dados do comprador pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os novos dados do comprador"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idComprador}")
    ResponseEntity<CompradorDTO> update(@PathVariable("idComprador") Integer idComprador,
                                        @Valid @RequestBody CompradorCreateDTO comprador)
            throws RegraDeNegocioException;

    @Operation(summary = "Deletar comprador por id", description = "Deleta o comprador selecionado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idComprador}")
    ResponseEntity<Void> delete(@PathVariable("idComprador") Integer idComprador) throws RegraDeNegocioException;

    @Operation(summary = "Listar compradores", description = "Lista todos os compradores cadastrados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de compradores cadastrados"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<CompradorDTO>> getAll() throws RegraDeNegocioException;

    @Operation(summary = "Buscar comprador por id", description = "Mostra os dados do comprador pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o comprador solicitado"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idComprador}")
    ResponseEntity<CompradorDTO> getById(@PathVariable("idComprador") Integer idComprador)
            throws RegraDeNegocioException;
}
