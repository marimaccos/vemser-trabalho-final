package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.docs.CompradorDoc;
import br.com.dbc.javamosdecolar.dto.CompradorCreateDTO;
import br.com.dbc.javamosdecolar.dto.CompradorDTO;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.service.CompradorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Validated
@RestController
@RequestMapping("/comprador")
@AllArgsConstructor
public class CompradorController implements CompradorDoc {

    private final CompradorService compradorService;

    @PostMapping
    public ResponseEntity<CompradorDTO> create(@Valid @RequestBody CompradorCreateDTO comprador)
                                                throws RegraDeNegocioException{
        return new ResponseEntity<>(compradorService.create(comprador), CREATED);
    }

    @PutMapping("/{idComprador}")
    public ResponseEntity<CompradorDTO> update(@PathVariable("idComprador") Integer idComprador,
                                               @Valid @RequestBody CompradorCreateDTO comprador)
                                                throws RegraDeNegocioException {
        return new ResponseEntity<>(compradorService.update(idComprador, comprador), OK);
    }

    @DeleteMapping("/{idComprador}")
    public ResponseEntity<Void> delete(@PathVariable("idComprador") Integer idComprador) throws RegraDeNegocioException {
        compradorService.delete(idComprador);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CompradorDTO>> getAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(compradorService.getAll(), OK);
    }

    @GetMapping("/{idComprador}")
    public ResponseEntity<CompradorDTO> getById(@PathVariable("idComprador") Integer idComprador)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(compradorService.getById(idComprador), OK);
    }
}

