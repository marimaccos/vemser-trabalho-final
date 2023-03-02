package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Passagem;
import br.com.dbc.javamosdecolar.model.dto.CreatePassagemDTO;
import br.com.dbc.javamosdecolar.model.dto.UpdatePassagemDTO;
import br.com.dbc.javamosdecolar.service.PassagemService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController()
@RequestMapping("/passagem")
public class PassagemController {
    private final PassagemService passagemService;

    public PassagemController(PassagemService passagemService) {
        this.passagemService = passagemService;
    }

    @GetMapping
    public List<Passagem> listar(@RequestParam(name="companhia", required = false) String companhia,
                                 @RequestParam(name="dataChegada", required = false) LocalDateTime dataChegada,
                                 @RequestParam(name="dataPartida", required = false) LocalDateTime dataPartida,
                                 @RequestParam(name="valor", required = false) BigDecimal valor) {
        return this.passagemService.listarPassagens(companhia, dataChegada, dataPartida, valor);
    }

    @GetMapping("/novas")
    public List<Passagem> listUltimasPassagens() throws RegraDeNegocioException {
        return this.passagemService.listarUltimasPassagens();
    }

    @PostMapping()
    public Passagem create(CreatePassagemDTO passagemDTO) throws RegraDeNegocioException {
        return this.passagemService.cadastrarPassagem(passagemDTO);
    }

    @PutMapping("/{idPassagem}")
    public void update(@PathVariable("idPassagem") Integer id, UpdatePassagemDTO passagemDTO)
            throws RegraDeNegocioException {
        this.passagemService.editarPassagem(id, passagemDTO);
    }

    @DeleteMapping("/{idPassagem}")
    public void delete(@PathVariable("idPassagem") Integer id) throws DatabaseException {
        this.passagemService.deletarPassagem(id);
    }
}
