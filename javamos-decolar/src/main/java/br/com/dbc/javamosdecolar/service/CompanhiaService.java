package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.*;
import br.com.dbc.javamosdecolar.dto.CompanhiaDTO;
import br.com.dbc.javamosdecolar.dto.CompanhiaCreateDTO;
import br.com.dbc.javamosdecolar.repository.CompanhiaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanhiaService {

    private final CompanhiaRepository companhiaRepository;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;


    public CompanhiaDTO criarCompanhia(CompanhiaCreateDTO companhiaDTO) throws RegraDeNegocioException {
        try {
            Usuario usuarioNovo = new Usuario(
                    companhiaDTO.getLogin(),
                    companhiaDTO.getSenha(),
                    companhiaDTO.getNome(),
                    TipoUsuario.COMPANHIA,
                    true);

            Usuario usuarioCriado = usuarioService.criarUsuario(usuarioNovo);
            Companhia companhia = objectMapper.convertValue(companhiaDTO, Companhia.class);
            companhia.setIdUsuario(usuarioCriado.getIdUsuario());

            Companhia companhiaNova = companhiaRepository.adicionar(companhia);
            return objectMapper.convertValue(companhiaNova, CompanhiaDTO.class);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public CompanhiaDTO getCompanhiaById(Integer id) throws RegraDeNegocioException {
        try {
            Companhia companhia = companhiaRepository.buscaCompanhiaPorId(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Companhia não encontrada"));
            return objectMapper.convertValue(companhia, CompanhiaDTO.class);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a recuperação da companhia.");
        }
    }

    public List<CompanhiaDTO> listaCompanhias() throws RegraDeNegocioException {
        try{
            List<CompanhiaDTO> listaCompradores = companhiaRepository.listaCompanhias().stream()
                    .map(companhia -> objectMapper.convertValue(companhia, CompanhiaDTO.class))
                    .collect(Collectors.toList());

            return listaCompradores;
        } catch (DatabaseException e) {
            e.printStackTrace();
        throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public Companhia getCompanhiaByNome(String nome) throws RegraDeNegocioException {
        try {
            return companhiaRepository.buscaCompanhiaPorNome(nome)
                            .orElseThrow(() -> new RegraDeNegocioException("Companhia não Encontrada"));
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a recuperação da companhia.");
        }
    }

    public Companhia editarCompanhia(Integer id, CompanhiaCreateDTO companhiaDTO) throws RegraDeNegocioException {
        try {
            Companhia companhia = companhiaRepository.buscaCompanhiaPorId(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Companhia não existe."));

            Usuario usuario = new Usuario(
                    companhia.getIdUsuario(),
                    companhiaDTO.getLogin(),
                    companhiaDTO.getSenha(),
                    companhiaDTO.getNome(),
                    TipoUsuario.COMPANHIA,
                    true);

            usuarioService.editarUsuario(companhia.getIdUsuario(), usuario);

            Companhia companhiaEditada = objectMapper.convertValue(companhiaDTO, Companhia.class);

            if(companhiaRepository.editarCompanhia(id, companhiaEditada)) {
                companhiaEditada.setIdCompanhia(id);
                return companhiaEditada;
            } else {
                throw new RegraDeNegocioException("Companhia não pode ser editada.");
            }
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public void deletarCompanhia(Integer idCompanhia) throws RegraDeNegocioException {
        try {
            Companhia companhiaEncontrada = companhiaRepository.buscaCompanhiaPorId(idCompanhia)
                    .orElseThrow(() -> new RegraDeNegocioException("Companhia não encontrada!"));

            usuarioService.desativarUsuario(companhiaEncontrada.getIdUsuario());

        }catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }
}
