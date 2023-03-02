package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Venda;
import br.com.dbc.javamosdecolar.model.dto.CreateVendaDTO;
import br.com.dbc.javamosdecolar.service.VendaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Validated
@RestController
@RequestMapping("/venda")
@AllArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @GetMapping("/{idComprador}/comprador")
    public ResponseEntity<List<Venda>> listaHistoricoComprasComprador(@PathVariable("idComprador") Integer id)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(vendaService.getHistoricoComprasComprador(id), OK);
    }

    @GetMapping("/{idCompanhia}/companhia")
    public ResponseEntity<List<Venda>> listaHistoricoVendasCompanhia(@PathVariable("idCompanhia") Integer id)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(vendaService.getHistoricoVendasCompanhia(id), OK);
    }

    @GetMapping()
    public ResponseEntity<Venda> getVendaPorCodigo(@RequestParam(name = "codigo", required = true) String uuid)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(vendaService.getVendaPorCodigo(uuid), OK);
    }

    @PostMapping
    public ResponseEntity<Venda> criar(@RequestBody @Valid CreateVendaDTO vendaDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(vendaService.efetuarVenda(vendaDTO), CREATED);
    }

    @PutMapping("/{idVenda}/cancelar")
    public ResponseEntity<Venda> cancelar(@PathVariable("idVenda") Integer idVenda) throws RegraDeNegocioException {
        vendaService.cancelarVenda(idVenda);
        return new ResponseEntity<>(NO_CONTENT);
    }

}
