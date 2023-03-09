package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.dto.CompanhiaCreateDTO;
import br.com.dbc.javamosdecolar.dto.CompanhiaDTO;
import br.com.dbc.javamosdecolar.service.CompanhiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/companhia")
public class CompanhiaController {
    private final CompanhiaService companhiaService;

    @GetMapping
    public ResponseEntity<List<CompanhiaDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity<>(companhiaService.listaCompanhias(), OK);
    }

    @GetMapping("/{idCompanhia}")
    public ResponseEntity<CompanhiaDTO> getCompanhiaById(@PathVariable("idCompanhia") Integer idCompanhia)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(companhiaService.getCompanhiaById(idCompanhia), OK);
    }

    @PostMapping
    public ResponseEntity<CompanhiaDTO> create(@Valid @RequestBody CompanhiaCreateDTO companhiaDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(companhiaService.criarCompanhia(companhiaDTO), OK);
    }

    @PutMapping("/{idCompanhia}")
    public ResponseEntity<Void> update(@PathVariable("idCompanhia") Integer idCompanhia,
                                       @Valid @RequestBody CompanhiaCreateDTO companhiaDTO) throws RegraDeNegocioException {
        companhiaService.editarCompanhia(idCompanhia, companhiaDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idCompanhia}")
    public ResponseEntity<Void> delete(@PathVariable("idCompanhia") Integer idCompanhia) throws RegraDeNegocioException {
        companhiaService.deletarCompanhia(idCompanhia);
        return ResponseEntity.ok().build();
    }
}
