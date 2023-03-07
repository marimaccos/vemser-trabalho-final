package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Comprador;
import br.com.dbc.javamosdecolar.model.TipoUsuario;
import br.com.dbc.javamosdecolar.model.Usuario;
import br.com.dbc.javamosdecolar.dto.CompradorCreateDTO;
import br.com.dbc.javamosdecolar.dto.CompradorDTO;
import br.com.dbc.javamosdecolar.repository.CompradorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompradorService {

    private final CompradorRepository compradorRepository;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public CompradorDTO getCompradorPorID(Integer idComprador) throws RegraDeNegocioException{
        try {
            Optional<Comprador> compradorEncontrado = compradorRepository.getCompradorPorID(idComprador);

            return objectMapper.convertValue(compradorEncontrado, CompradorDTO.class);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a busca.");
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

    public CompradorDTO editarComprador(Integer idComprador, CompradorCreateDTO comprador) throws RegraDeNegocioException{
        try {
            // Retorna o comprador existente
            Optional<Comprador> compradorEncontrado = compradorRepository.getCompradorPorID(idComprador);
            // Converte o Optional para Comprador para pegar o idUsuario
            Comprador compradorConvertido = objectMapper.convertValue(compradorEncontrado, Comprador.class);
            // Cria usuario e passa os dados para edição
            Usuario usuarioEncontrado = usuarioService.buscarUsuarioById(compradorConvertido.getIdUsuario());
            usuarioEncontrado.setNome(comprador.getNome());
            usuarioEncontrado.setSenha(comprador.getSenha());
            usuarioService.editarUsuario(usuarioEncontrado);

            return getCompradorPorID(idComprador);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void deletarComprador(Integer idComprador) {
        // TODO implementar
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
