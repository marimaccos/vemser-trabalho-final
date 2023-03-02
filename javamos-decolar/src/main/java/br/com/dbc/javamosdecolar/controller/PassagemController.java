package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Passagem;
import br.com.dbc.javamosdecolar.model.dto.CreatePassagemDTO;
import br.com.dbc.javamosdecolar.model.dto.UpdatePassagemDTO;
import br.com.dbc.javamosdecolar.service.PassagemService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController()
@RequestMapping("/passagem")
@AllArgsConstructor
public class PassagemController {
    private final PassagemService passagemService;

    @GetMapping("/q")
    public List<Passagem> listarPorParametros(@RequestParam(name="companhia", required = false) String companhia,
                                 @RequestParam(name="dataChegada", required = false) String dataChegada,
                                 @RequestParam(name="dataPartida", required = false) String dataPartida,
                                 @RequestParam(name="valor", required = false) BigDecimal valor)
            throws RegraDeNegocioException {
        return this.passagemService.listarPassagens(companhia, dataChegada, dataPartida, valor);
    }

    @GetMapping("/new")
    public List<Passagem> listUltimasPassagens() throws RegraDeNegocioException {
        return this.passagemService.listarUltimasPassagens();
    }

    @GetMapping("/{idPassagem}")
    public Passagem getPassagemById(@RequestParam("idPassagem") Integer id) throws RegraDeNegocioException {
        return this.passagemService.getPassagemById(id);
    }

    @PostMapping()
    public Passagem create(@RequestBody @Valid CreatePassagemDTO passagemDTO) throws RegraDeNegocioException {
        return this.passagemService.cadastrarPassagem(passagemDTO);
    }

    @PutMapping("/{idPassagem}")
    public void update(@PathVariable("idPassagem") Integer id,
                       @RequestBody @Valid UpdatePassagemDTO passagemDTO)
            throws RegraDeNegocioException {
        this.passagemService.editarPassagem(id, passagemDTO);
    }

    @DeleteMapping("/{idPassagem}")
    public void delete(@PathVariable("idPassagem") Integer id) throws DatabaseException {
        this.passagemService.deletarPassagem(id);
    }
}
