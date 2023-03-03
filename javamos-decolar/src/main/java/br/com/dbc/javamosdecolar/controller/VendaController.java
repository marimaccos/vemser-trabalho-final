package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Venda;
import br.com.dbc.javamosdecolar.model.dto.CreateVendaDTO;
import br.com.dbc.javamosdecolar.model.dto.VendaDTO;
import br.com.dbc.javamosdecolar.service.VendaService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Validated
@RestController
@RequestMapping("/venda")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @GetMapping("/{idComprador}/comprador")
    public ResponseEntity<List<VendaDTO>> listaHistoricoComprasComprador(@PathVariable("idComprador") Integer id)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(vendaService.getHistoricoComprasComprador(id), OK);
    }

    @GetMapping("/{idCompanhia}/companhia")
    public ResponseEntity<List<VendaDTO>> listaHistoricoVendasCompanhia(@PathVariable("idCompanhia") Integer id)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(vendaService.getHistoricoVendasCompanhia(id), OK);
    }

    @GetMapping()
    public ResponseEntity<VendaDTO> getVendaPorCodigo(@RequestParam(name = "codigo", required = true) String uuid)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(vendaService.getVendaPorCodigo(uuid), OK);
    }

    @PostMapping
    public ResponseEntity<VendaDTO> criar(@RequestBody @Valid CreateVendaDTO vendaDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(vendaService.efetuarVenda(vendaDTO), CREATED);
    }

    @DeleteMapping("/{idVenda}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable("idVenda") Integer idVenda) throws RegraDeNegocioException {
        vendaService.cancelarVenda(idVenda);
        return ResponseEntity.noContent().build();
    }

}
