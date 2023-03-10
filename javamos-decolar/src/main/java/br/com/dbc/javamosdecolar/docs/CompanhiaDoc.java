package br.com.dbc.javamosdecolar.docs;

import br.com.dbc.javamosdecolar.dto.CompanhiaCreateDTO;
import br.com.dbc.javamosdecolar.dto.CompanhiaDTO;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Companhia", description = "Endpoints de companhia")
public interface CompanhiaDoc {

    @Operation(summary = "Criar companhia", description = "Cria uma nova companhia")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna a companhia criada"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<CompanhiaDTO> create(@Valid @RequestBody CompanhiaCreateDTO companhiaDTO) throws RegraDeNegocioException;

    @Operation(summary = "Editar companhia por id", description = "Edita os dados da companhia pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os novos dados da companhia"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idCompanhia}")
    ResponseEntity<CompanhiaDTO> update(@PathVariable("idCompanhia") Integer idCompanhia,
                                        @Valid @RequestBody CompanhiaCreateDTO companhiaDTO) throws RegraDeNegocioException;

    @Operation(summary = "Deletar companhia por id", description = "Deleta a companhia selecionado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idCompanhia}")
    ResponseEntity<Void> delete(@PathVariable("idCompanhia") Integer idCompanhia) throws RegraDeNegocioException;

    @Operation(summary = "Listar companhias", description = "Lista todas as companhias cadastradas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de companhias cadastradas"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<CompanhiaDTO>> getAll() throws RegraDeNegocioException;

    @Operation(summary = "Buscar companhia por id", description = "Mostra os dados da companhia pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a companhia solicitada"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCompanhia}")
    ResponseEntity<CompanhiaDTO> getById(@PathVariable("idCompanhia") Integer idCompanhia)
            throws RegraDeNegocioException;
}
