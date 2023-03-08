package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Comprador;
import br.com.dbc.javamosdecolar.model.Status;
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
    private final VendaService vendaService;
    private final ObjectMapper objectMapper;

    public CompradorDTO getCompradorPorID(Integer idComprador) throws RegraDeNegocioException{
        try {
            Comprador compradorEncontrado = compradorRepository.getCompradorPorId(idComprador)
                    .orElseThrow(() -> new RegraDeNegocioException("Comprador não encontrado!"));

            return objectMapper.convertValue(compradorEncontrado, CompradorDTO.class);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a busca.");
        }
    }

    public CompradorDTO criarComprador(CompradorCreateDTO compradorDTO) throws RegraDeNegocioException {
        try {
            Usuario usuarioNovo = new Usuario(
                    compradorDTO.getLogin(),
                    compradorDTO.getSenha(),
                    compradorDTO.getNome(),
                    TipoUsuario.COMPRADOR,
                    true);

            Usuario usuarioCriado = usuarioService.criarUsuario(usuarioNovo);
            Comprador comprador = objectMapper.convertValue(compradorDTO, Comprador.class);
            comprador.setIdUsuario(usuarioCriado.getIdUsuario());

            Comprador compradorNovo= compradorRepository.adicionar(comprador);
            return objectMapper.convertValue(compradorNovo, CompradorDTO.class);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public CompradorDTO editarComprador(Integer idComprador, CompradorCreateDTO compradorDTO)
            throws RegraDeNegocioException{
        try {
            // Retorna o comprador existente
            Comprador comprador = compradorRepository.getCompradorPorId(idComprador)
                    .orElseThrow(() -> new RegraDeNegocioException("Comprador não encontrado!"));

            // Cria usuario e passa os dados para edição

            Usuario usuario = new Usuario(
                    comprador.getIdUsuario(),
                    compradorDTO.getLogin(),
                    compradorDTO.getSenha(),
                    compradorDTO.getNome(),
                    TipoUsuario.COMPRADOR,
                    true);

            Usuario usuarioEditado = usuarioService.editarUsuario(comprador.getIdUsuario(), usuario);
            comprador.setNome(usuarioEditado.getNome());

            return objectMapper.convertValue(comprador, CompradorDTO.class);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void deletarComprador(Integer idComprador) throws RegraDeNegocioException {
        try {
            Comprador compradorEncontrado = compradorRepository.getCompradorPorId(idComprador)
                    .orElseThrow(() -> new RegraDeNegocioException("Comprador não encontrado!"));

            boolean vendasAtivas = vendaService.getHistoricoComprasComprador(idComprador).stream()
                    .anyMatch(venda -> venda.getStatus().equals(Status.CONCLUIDO));

            if (vendasAtivas) {
                throw new RegraDeNegocioException("Comprador não pode ser deletado! Existem vendas ativas.");
            } else {
                usuarioService.desativarUsuario(compradorEncontrado.getIdUsuario());
            }

        }catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public List<CompradorDTO> listaCompradores() throws RegraDeNegocioException {
        try {
            List<CompradorDTO> listaCompradores = compradorRepository.listaCompradores().stream()
                    .map(comprador -> objectMapper.convertValue(comprador, CompradorDTO.class))
                    .toList();

            return listaCompradores;

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }
}
