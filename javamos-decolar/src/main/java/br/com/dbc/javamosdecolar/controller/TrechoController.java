package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.dto.TrechoCreateDTO;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.dto.TrechoDTO;
import br.com.dbc.javamosdecolar.service.TrechoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/trecho")
@AllArgsConstructor
public class TrechoController {

    private final TrechoService trechoService;

    @GetMapping
    public ResponseEntity<List<TrechoDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity<>(trechoService.listaTrechos(), HttpStatus.OK);
    }

    @GetMapping("/{idTrecho}")
    public ResponseEntity<TrechoDTO> getTrechoById(@PathVariable("idTrecho") Integer idTrecho)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(trechoService.getTrechoById(idTrecho), HttpStatus.OK);
    }

    @GetMapping("/{idCompanhia}")
    public ResponseEntity<List<TrechoDTO>> getTrechosPorCompanhia(@PathVariable("idCompanhia") Integer idCompanhia)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(trechoService.getTrechosPorCompanhia(idCompanhia), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TrechoDTO> create(@Valid @RequestBody TrechoCreateDTO trecho)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(trechoService.criarTrecho(trecho), HttpStatus.OK);
    }

    @PutMapping("/{idTrecho}")
    public ResponseEntity<TrechoDTO> update(@PathVariable("idTrecho") Integer idTrecho, @Valid @RequestBody TrechoCreateDTO trecho)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(trechoService.editarTrecho(idTrecho, trecho), HttpStatus.OK);
    }

    @DeleteMapping("/{idTrecho}")
    public ResponseEntity<TrechoDTO> delete(@PathVariable("idTrecho") Integer idTrecho)
            throws RegraDeNegocioException {
        trechoService.deletarTrecho(idTrecho);
        return ResponseEntity.noContent().build();
    }

}
