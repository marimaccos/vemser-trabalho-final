package br.com.dbc.javamosdecolar.docs;

import br.com.dbc.javamosdecolar.dto.PassagemCreateDTO;
import br.com.dbc.javamosdecolar.dto.PassagemDTO;
import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

public interface PassagemDoc {

    @Operation(summary = "Listar ultimas passagens", description = "Lista as ultimas passagens cadastradas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista solicitada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/new")
    public ResponseEntity<List<PassagemDTO>> listUltimasPassagens() throws RegraDeNegocioException;

    @Operation(summary = "Buscar passagem por data", description = "Lista as passagens por data")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de passagens solicitada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/data")
    public ResponseEntity<List<PassagemDTO>> listPassagemPorData(@RequestParam(value = "dataPartida", required = false) String dataPartida,
                                                                 @RequestParam(value = "dataChegada", required = false) String dataChegada)
            throws RegraDeNegocioException;

    @Operation(summary = "Buscar passagens por id da companhia", description = "Lista as passagens id da companhia")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de passagens solicitada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/companhia")
    public ResponseEntity<List<PassagemDTO>> listPassagemPorCompanhia(@RequestParam("nome") String nome) throws RegraDeNegocioException;

    @Operation(summary = "Buscar passagem por valor", description = "Lista as passagens até o limite de valor selecionado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de passagens solicitadas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/valor")
    public ResponseEntity<List<PassagemDTO>> listUltimasPassagens(@RequestParam("max") BigDecimal valor) throws RegraDeNegocioException;

    @Operation(summary = "Buscar passagem por id", description = "Mostra os dados da passagem pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a passagem solicitada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idPassagem}")
    public ResponseEntity<PassagemDTO> getPassagemById(@PathVariable("idPassagem") Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Criar passagem", description = "Cria uma nova passagem")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a passagem criada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<PassagemDTO> create(@RequestBody @Valid PassagemCreateDTO passagemDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Editar passagem por id", description = "Edita os dados da passagem pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os novos dados da passagem"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPassagem}")
    public ResponseEntity<Void> update(@PathVariable("idPassagem") Integer id,
                                       @RequestBody @Valid PassagemCreateDTO passagemDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Deletar passagem por id", description = "Deleta a passagem selecionada")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna que a operação foi realizada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPassagem}")
    public ResponseEntity<Void> delete(@PathVariable("idPassagem") Integer id) throws DatabaseException;
}
