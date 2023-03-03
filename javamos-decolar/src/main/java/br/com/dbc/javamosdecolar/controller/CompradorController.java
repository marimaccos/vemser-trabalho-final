package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.model.Comprador;
import br.com.dbc.javamosdecolar.service.CompradorService;
import br.com.dbc.javamosdecolar.service.PassagemService;
import br.com.dbc.javamosdecolar.service.VendaService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comprador")
@AllArgsConstructor
public class CompradorController {

    private final CompradorService compradorService;
    private final PassagemService passagemService;
    private final VendaService vendaService;

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Comprador>> list() {
        return new ResponseEntity<>(compradorService.list(), HttpStatus.OK);
    }

    // GET ONE
    @GetMapping("/${idComprador}")
    public ResponseEntity<Comprador> getCompradorByID(@PathVariable("idComprador") Integer idComprador) {
        return new ResponseEntity<>(compradorService.getCompradorByID(idComprador), HttpStatus.OK);
    }

    // POST CREATE
    @PostMapping
    public ResponseEntity<Comprador> create(@RequestBody Comprador comprador) {
        return new ResponseEntity<>(compradorService.create(), HttpStatus.OK);
    }

    // PUT UPDATE
    @PutMapping("/idComprador")
    public ResponseEntity<Comprador> update(Integer idComprador, @RequestBody Comprador comprador) {
        return new ResponseEntity<>(compradorService.update(idComprador, comprador), HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{idComprador}")
    public ResponseEntity<Void> delete(@PathVariable("idComprador") Integer idComprador)  {
        compradorService.delete(idComprador);
        return ResponseEntity.ok().build();
    }

}

