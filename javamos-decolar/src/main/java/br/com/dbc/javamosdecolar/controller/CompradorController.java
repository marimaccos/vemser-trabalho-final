package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Comprador;
import br.com.dbc.javamosdecolar.model.dto.CompradorDTO;
import br.com.dbc.javamosdecolar.model.dto.CompradorCreateDTO;
import br.com.dbc.javamosdecolar.service.CompradorService;
import br.com.dbc.javamosdecolar.service.PassagemService;
import br.com.dbc.javamosdecolar.service.VendaService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/comprador")
@AllArgsConstructor
public class CompradorController {

    private final CompradorService compradorService;
    private final PassagemService passagemService;
    private final VendaService vendaService;

    // GET ALL
    @GetMapping
    public ResponseEntity<List<CompradorDTO>> listaCompradores() throws RegraDeNegocioException {
        return new ResponseEntity<>(compradorService.listaCompradores(), HttpStatus.OK);
    }

    // GET ONE
    @GetMapping("/${idComprador}")
    public ResponseEntity<CompradorDTO> getCompradorByID(@PathVariable("idComprador") Integer idComprador)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(compradorService.getCompradorPorID(idComprador), HttpStatus.OK);
    }

    // POST CREATE
    @PostMapping
    public ResponseEntity<CompradorDTO> cadastrar(@Valid @RequestBody CompradorCreateDTO comprador,
                                                  @Valid @RequestBody UsuarioCreateDTO usuario) {
        return new ResponseEntity<>(compradorService.cadastrar(comprador, usuario), HttpStatus.OK);
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

