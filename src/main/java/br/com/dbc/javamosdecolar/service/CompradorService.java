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

@Service
@AllArgsConstructor
public class CompradorService {

    private final CompradorRepository compradorRepository;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public List<CompradorDTO> getAll() throws RegraDeNegocioException {
        try {
            List<CompradorDTO> listaCompradores = compradorRepository.getAll().stream()
                    .map(comprador -> objectMapper.convertValue(comprador, CompradorDTO.class))
                    .toList();

            return listaCompradores;

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public CompradorDTO create(CompradorCreateDTO compradorDTO) throws RegraDeNegocioException {
        try {
            Usuario usuarioNovo = new Usuario(
                    compradorDTO.getLogin(),
                    compradorDTO.getSenha(),
                    compradorDTO.getNome(),
                    TipoUsuario.COMPRADOR,
                    true);

            Usuario usuarioCriado = usuarioService.create(usuarioNovo);
            Comprador comprador = objectMapper.convertValue(compradorDTO, Comprador.class);
            comprador.setIdUsuario(usuarioCriado.getIdUsuario());

            CompradorDTO compradorCriado = objectMapper.convertValue(compradorRepository.create(comprador),
                    CompradorDTO.class);
            compradorCriado.setAtivo(usuarioCriado.isAtivo());

            return compradorCriado;

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public CompradorDTO update(Integer idComprador, CompradorCreateDTO compradorDTO)
            throws RegraDeNegocioException{
        try {
            // Retorna o comprador existente
            Comprador comprador = compradorRepository.getById(idComprador)
                    .orElseThrow(() -> new RegraDeNegocioException("Comprador não encontrado!"));

            // Cria usuario e passa os dados para edição
            Usuario usuario = new Usuario(
                    comprador.getIdUsuario(),
                    compradorDTO.getLogin(),
                    compradorDTO.getSenha(),
                    compradorDTO.getNome(),
                    TipoUsuario.COMPRADOR,
                    true);

            Usuario usuarioEditado = usuarioService.update(comprador.getIdUsuario(), usuario);
            comprador.setNome(usuarioEditado.getNome());

            return objectMapper.convertValue(comprador, CompradorDTO.class);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void delete(Integer idComprador) throws RegraDeNegocioException {
        try {
            Comprador compradorEncontrado = compradorRepository.getById(idComprador)
                    .orElseThrow(() -> new RegraDeNegocioException("Comprador não encontrado!"));

            usuarioService.delete(compradorEncontrado.getIdUsuario());

        }catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public CompradorDTO getById(Integer idComprador) throws RegraDeNegocioException{
        try {
            Comprador compradorEncontrado = compradorRepository.getById(idComprador)
                    .orElseThrow(() -> new RegraDeNegocioException("Comprador não encontrado!"));

            return objectMapper.convertValue(compradorEncontrado, CompradorDTO.class);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a busca.");
        }
    }
}
