package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.dto.CompradorCreateDTO;
import br.com.dbc.javamosdecolar.dto.CompradorDTO;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.service.CompradorService;
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

    @GetMapping
    public ResponseEntity<List<CompradorDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity<>(compradorService.listaCompradores(), HttpStatus.OK);
    }

    @GetMapping("/{idComprador}")
    public ResponseEntity<CompradorDTO> getCompradorByID(@PathVariable("idComprador") Integer idComprador)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(compradorService.getCompradorPorID(idComprador), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompradorDTO> create(@Valid @RequestBody CompradorCreateDTO comprador)
                                                throws RegraDeNegocioException{
        return new ResponseEntity<>(compradorService.criarComprador(comprador), HttpStatus.OK);
    }

    @PutMapping("/{idComprador}")
    public ResponseEntity<CompradorDTO> update(@PathVariable("idComprador") Integer idComprador,
                                               @Valid @RequestBody CompradorCreateDTO comprador)
                                                throws RegraDeNegocioException {
        return new ResponseEntity<>(compradorService.editarComprador(idComprador, comprador), HttpStatus.OK);
    }
    
    @DeleteMapping("/{idComprador}")
    public ResponseEntity<Void> delete(@PathVariable("idComprador") Integer idComprador)  {
        compradorService.deletarComprador(idComprador);
        return ResponseEntity.ok().build();
    }

}

