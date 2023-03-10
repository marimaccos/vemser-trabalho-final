package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.docs.PassagemDoc;
import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.dto.PassagemCreateDTO;
import br.com.dbc.javamosdecolar.dto.PassagemDTO;
import br.com.dbc.javamosdecolar.service.PassagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController()
@RequestMapping("/passagem")
@RequiredArgsConstructor
public class PassagemController implements PassagemDoc {
    private final PassagemService passagemService;

    @PostMapping
    public ResponseEntity<PassagemDTO> create(@RequestBody @Valid PassagemCreateDTO passagemDTO)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(this.passagemService.create(passagemDTO), CREATED);
    }

    @PutMapping("/{idPassagem}")
    public ResponseEntity<PassagemDTO> update(@PathVariable("idPassagem") Integer id,
                                              @RequestBody @Valid PassagemCreateDTO passagemDTO)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(this.passagemService.update(id, passagemDTO), OK);
    }

    @DeleteMapping("/{idPassagem}")
    public ResponseEntity<Void> delete(@PathVariable("idPassagem") Integer id) throws DatabaseException {
        this.passagemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/new")
    public ResponseEntity<List<PassagemDTO>> getUltimasPassagens() throws RegraDeNegocioException {
        return new ResponseEntity<>(this.passagemService.getUltimasPassagens(), OK);
    }

    @GetMapping("/data")
    public ResponseEntity<List<PassagemDTO>> getByData(
            @RequestParam(value = "dataPartida", required = false) String dataPartida,
            @RequestParam(value = "dataChegada", required = false) String dataChegada)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(this.passagemService.getByData(dataChegada, dataPartida), OK);
    }

    @GetMapping("/companhia")
    public ResponseEntity<List<PassagemDTO>> getByCompanhia(
            @RequestParam("nome") String nome) throws RegraDeNegocioException {
        return new ResponseEntity<>(this.passagemService.getByCompanhia(nome), OK);
    }

    @GetMapping("/valor")
    public ResponseEntity<List<PassagemDTO>> getByValorMaximo(
            @RequestParam("max") BigDecimal valor) throws RegraDeNegocioException {
        return new ResponseEntity<>(this.passagemService.getByValorMaximo(valor), OK);
    }

    @GetMapping("/{idPassagem}")
    public ResponseEntity<PassagemDTO> getById(
            @PathVariable("idPassagem") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(this.passagemService.getById(id), OK);
    }
}
