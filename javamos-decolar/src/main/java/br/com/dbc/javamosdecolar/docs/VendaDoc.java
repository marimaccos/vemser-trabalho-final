package br.com.dbc.javamosdecolar.docs;

import br.com.dbc.javamosdecolar.dto.VendaCreateDTO;
import br.com.dbc.javamosdecolar.dto.VendaDTO;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Venda", description = "Endpoints de venda")
public interface VendaDoc {

    @Operation(summary = "Efetuar Venda", description = "Lista todas as companhias cadastradas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Venda realizada com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<VendaDTO> create(@RequestBody @Valid VendaCreateDTO vendaDTO) throws RegraDeNegocioException;

    @Operation(summary = "Cancelar venda", description = "Cancela uma venda realizada")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idVenda}/cancelar")
    ResponseEntity<Void> delete(@PathVariable("idVenda") Integer idVenda) throws RegraDeNegocioException;

    @Operation(summary = "Historico de compras do comprador", description = "Lista o historico de compras do comprador")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de companhias cadastradas"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idComprador}/comprador")
    ResponseEntity<List<VendaDTO>> getByHistoricoCompras(@PathVariable("idComprador") Integer id)
            throws RegraDeNegocioException;

    @Operation(summary = "Historico de vendas da companhia", description = "Lista o historico de vendas da companhia")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de companhias cadastradas"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCompanhia}/companhia")
    ResponseEntity<List<VendaDTO>> getByHistoricoVendas(@PathVariable("idCompanhia") Integer id)
            throws RegraDeNegocioException;

    @Operation(summary = "Pegar venda por código", description = "Pega uma venda pelo código")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de companhias cadastradas"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping()
    ResponseEntity<VendaDTO> getByCodigo(@RequestParam(name = "codigo") String uuid)
            throws RegraDeNegocioException;
}
