package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.model.Venda;
import br.com.dbc.javamosdecolar.model.dto.CreateVendaDTO;
import br.com.dbc.javamosdecolar.service.VendaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venda")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    public List<Venda> criar(@RequestBody CreateVendaDTO vendaDTO) {
        return vendaService.efetuarVenda(vendaDTO);
    }

    @PutMapping("/{idVenda}/cancelar")
    public void cancelar(@PathVariable("idVenda") Integer idVenda) {
        vendaService.cancelarVenda(idVenda);
    }

}
