package br.com.dbc.javamosdecolar.docs;

import br.com.dbc.javamosdecolar.dto.PassagemCreateDTO;
import br.com.dbc.javamosdecolar.dto.PassagemDTO;
import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Passagem", description = "Endpoints de passagem")
public interface PassagemDoc {

    @Operation(summary = "Criar passagem", description = "Cria uma nova passagem")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna a passagem criada"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<PassagemDTO> create(@RequestBody @Valid PassagemCreateDTO passagemDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Editar passagem por id", description = "Edita os dados da passagem pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os novos dados da passagem"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPassagem}")
    ResponseEntity<PassagemDTO> update(@PathVariable("idPassagem") Integer id,
                                       @RequestBody @Valid PassagemCreateDTO passagemDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Deletar passagem por id", description = "Deleta a passagem selecionada")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPassagem}")
    ResponseEntity<Void> delete(@PathVariable("idPassagem") Integer id) throws DatabaseException;

    @Operation(summary = "Listar ultimas passagens", description = "Lista as ultimas passagens cadastradas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista solicitada"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/new")
    ResponseEntity<List<PassagemDTO>> getUltimasPassagens() throws RegraDeNegocioException;

    @Operation(summary = "Buscar passagem por data", description = "Lista as passagens por data")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de passagens solicitada"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/data")
    ResponseEntity<List<PassagemDTO>> getByData(@RequestParam(value = "dataPartida", required = false) String dataPartida,
                                                @RequestParam(value = "dataChegada", required = false) String dataChegada)
            throws RegraDeNegocioException;

    @Operation(summary = "Buscar passagens por id da companhia", description = "Lista as passagens id da companhia")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de passagens solicitada"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/companhia")
    ResponseEntity<List<PassagemDTO>> getByCompanhia(@RequestParam("nome") String nome) throws RegraDeNegocioException;

    @Operation(summary = "Buscar passagem por valor", description = "Lista as passagens até o limite de valor selecionado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de passagens solicitadas"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/valor")
    ResponseEntity<List<PassagemDTO>> getByValorMaximo(@RequestParam("max") BigDecimal valor) throws RegraDeNegocioException;

    @Operation(summary = "Buscar passagem por id", description = "Mostra os dados da passagem pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a passagem solicitada"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idPassagem}")
    ResponseEntity<PassagemDTO> getById(@PathVariable("idPassagem") Integer id) throws RegraDeNegocioException;
}
