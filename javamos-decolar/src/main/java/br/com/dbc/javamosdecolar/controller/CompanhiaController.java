package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.docs.CompanhiaDoc;
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
public class CompanhiaController implements CompanhiaDoc {
    private final CompanhiaService companhiaService;

    @PostMapping
    public ResponseEntity<CompanhiaDTO> create(@Valid @RequestBody CompanhiaCreateDTO companhiaDTO)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(companhiaService.create(companhiaDTO), CREATED);
    }

    @PutMapping("/{idCompanhia}")
    public ResponseEntity<CompanhiaDTO> update(@PathVariable("idCompanhia") Integer idCompanhia,
                                               @Valid @RequestBody CompanhiaCreateDTO companhiaDTO)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(companhiaService.update(idCompanhia, companhiaDTO), OK);
    }

    @DeleteMapping("/{idCompanhia}")
    public ResponseEntity<Void> delete(@PathVariable("idCompanhia") Integer idCompanhia)
            throws RegraDeNegocioException {
        companhiaService.delete(idCompanhia);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CompanhiaDTO>> getAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(companhiaService.getAll(), OK);
    }

    @GetMapping("/{idCompanhia}")
    public ResponseEntity<CompanhiaDTO> getById(@PathVariable("idCompanhia") Integer idCompanhia)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(companhiaService.getById(idCompanhia), OK);
    }
}
