package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Passagem;
import br.com.dbc.javamosdecolar.model.dto.CreatePassagemDTO;
import br.com.dbc.javamosdecolar.model.dto.UpdatePassagemDTO;
import br.com.dbc.javamosdecolar.service.PassagemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Validated
@RestController()
@RequestMapping("/passagem")
@AllArgsConstructor
public class PassagemController {
    private final PassagemService passagemService;

    @GetMapping("/q")
    public ResponseEntity<List<Passagem>> listarPorParametros(@RequestParam(name="companhia", required = false) String companhia,
                                                             @RequestParam(name="dataChegada", required = false) String dataChegada,
                                                             @RequestParam(name="dataPartida", required = false) String dataPartida,
                                                             @RequestParam(name="valor", required = false) BigDecimal valor)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(this.passagemService.listarPassagens(companhia, dataChegada, dataPartida, valor), OK);
    }

    @GetMapping("/new")
    public ResponseEntity<List<Passagem>> listUltimasPassagens() throws RegraDeNegocioException {
        return new ResponseEntity<>(this.passagemService.listarUltimasPassagens(), OK);
    }

    @GetMapping("/{idPassagem}")
    public ResponseEntity<Passagem> getPassagemById(@RequestParam("idPassagem") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(this.passagemService.getPassagemById(id), OK);
    }

    @PostMapping()
    public ResponseEntity<Passagem> create(@RequestBody @Valid CreatePassagemDTO passagemDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(this.passagemService.cadastrarPassagem(passagemDTO), CREATED);
    }

    @PutMapping("/{idPassagem}")
    public ResponseEntity<Void> update(@PathVariable("idPassagem") Integer id,
                       @RequestBody @Valid UpdatePassagemDTO passagemDTO)
            throws RegraDeNegocioException {
        this.passagemService.editarPassagem(id, passagemDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idPassagem}")
    public ResponseEntity<Void> delete(@PathVariable("idPassagem") Integer id) throws DatabaseException {
        this.passagemService.deletarPassagem(id);
        return ResponseEntity.noContent().build();
    }
}
