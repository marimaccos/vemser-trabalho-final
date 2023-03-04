package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Comprador;
import br.com.dbc.javamosdecolar.model.TipoUsuario;
import br.com.dbc.javamosdecolar.model.Usuario;
import br.com.dbc.javamosdecolar.model.dto.CompradorCreateDTO;
import br.com.dbc.javamosdecolar.model.dto.CompradorDTO;
import br.com.dbc.javamosdecolar.repository.CompradorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompradorService {

    private final CompradorRepository compradorRepository;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public Comprador getCompradorPorID(Integer idComprador) throws RegraDeNegocioException{
        try {
            Comprador compradorEncontrado = compradorRepository.getCompradorPorID(idComprador)
                    .orElseThrow(() -> new RegraDeNegocioException("Comprador não encontrado!"));

            return compradorEncontrado;

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public CompradorDTO criarComprador(CompradorCreateDTO comprador) throws RegraDeNegocioException {
        try {
            Usuario usuarioNovo = new Usuario(comprador.getLogin(), comprador.getSenha(), comprador.getNome(), TipoUsuario.COMPRADOR);
            Usuario usuarioCriado = usuarioService.criarUsuario(usuarioNovo);

            Comprador compradorNovo = objectMapper.convertValue(comprador, Comprador.class);
            compradorNovo.setIdUsuario(usuarioCriado.getIdUsuario());
            compradorNovo = compradorRepository.criarComprador(compradorNovo);

            return objectMapper.convertValue(compradorNovo, CompradorDTO.class);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public CompradorDTO update(Integer idComprador, CompradorCreateDTO comprador) throws RegraDeNegocioException{
        try {
            Comprador compradorEncontrado = getCompradorPorID(idComprador);
            



        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante o update.");
        }
    }

    public List<CompradorDTO> listaCompradores() throws RegraDeNegocioException{
        try {
            List<CompradorDTO> listaCompradores = compradorRepository.listaCompradores().stream()
                    .map(comprador -> objectMapper.convertValue(comprador, CompradorDTO.class))
                    .toList();

            return listaCompradores;

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }
}
