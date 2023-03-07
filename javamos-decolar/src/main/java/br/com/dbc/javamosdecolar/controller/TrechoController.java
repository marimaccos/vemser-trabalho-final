package br.com.dbc.javamosdecolar.controller;

import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.dto.TrechoDTO;
import br.com.dbc.javamosdecolar.service.TrechoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
