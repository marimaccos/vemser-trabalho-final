package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Companhia;
import br.com.dbc.javamosdecolar.model.dto.CompanhiaCreateDTO;
import br.com.dbc.javamosdecolar.model.dto.CompanhiaDTO;
import br.com.dbc.javamosdecolar.model.dto.CompradorDTO;
import br.com.dbc.javamosdecolar.service.CompanhiaService;
import br.com.dbc.javamosdecolar.service.CompradorService;
import br.com.dbc.javamosdecolar.service.PassagemService;
import br.com.dbc.javamosdecolar.service.VendaService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/companhia")
public class CompanhiaController {
    private final CompanhiaService companhiaService;
    private final PassagemService passagemService;
    private final VendaService vendaService;

    @GetMapping
    public ResponseEntity<List<CompanhiaDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity<>(companhiaService.listaCompanhias(), OK);
    }

    @GetMapping("/${idCompanhia}")
    public ResponseEntity<CompanhiaDTO> getCompanhiaById(@PathVariable("idCompanhia") Integer idCompanhia)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(companhiaService.getCompanhiaByIdDTO(idCompanhia), OK);
    }

    @PostMapping
    public ResponseEntity<CompanhiaDTO> create(@Valid @RequestBody CompanhiaCreateDTO companhiaDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(companhiaService.criarCompanhia(companhiaDTO), OK);
    }

    @PutMapping("/idCompanhia")
    public ResponseEntity<Void> update(@PathVariable("idCompanhia") Integer idCompanhia,
                                       @Valid @RequestBody CompanhiaCreateDTO companhiaDTO) throws RegraDeNegocioException {
        companhiaService.editarCompanhia(idCompanhia, companhiaDTO);
        return ResponseEntity.noContent().build();
    }
}
