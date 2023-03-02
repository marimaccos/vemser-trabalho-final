package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Venda;
import br.com.dbc.javamosdecolar.model.dto.CreateVendaDTO;
import br.com.dbc.javamosdecolar.service.VendaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venda")
@AllArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @GetMapping("/{idComprador}")
    public List<Venda> listaHistoricoVendasComprador(@PathVariable("idComprador") Integer id) throws RegraDeNegocioException {
        return vendaService.getHistoricoVendasComprador(id);
    }

    @PostMapping
    public Venda criar(@RequestBody CreateVendaDTO vendaDTO) throws RegraDeNegocioException {
        return vendaService.efetuarVenda(vendaDTO);
    }

    @PutMapping("/{idVenda}/cancelar")
    public void cancelar(@PathVariable("idVenda") Integer idVenda) throws RegraDeNegocioException {
        vendaService.cancelarVenda(idVenda);
    }

}
