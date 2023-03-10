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

    public List<CompanhiaDTO> getAll() throws RegraDeNegocioException {
        try{
            List<CompanhiaDTO> listaCompradores = companhiaRepository.getAll().stream()
                    .map(companhia -> objectMapper.convertValue(companhia, CompanhiaDTO.class))
                    .collect(Collectors.toList());

            return listaCompradores;

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public CompanhiaDTO create(CompanhiaCreateDTO companhiaDTO) throws RegraDeNegocioException {
        try {
            Usuario usuarioNovo = new Usuario(
                    companhiaDTO.getLogin(),
                    companhiaDTO.getSenha(),
                    companhiaDTO.getNome(),
                    TipoUsuario.COMPANHIA,
                    true);

            Usuario usuarioCriado = usuarioService.create(usuarioNovo);
            Companhia companhia = objectMapper.convertValue(companhiaDTO, Companhia.class);
            companhia.setIdUsuario(usuarioCriado.getIdUsuario());

            CompanhiaDTO companhiaCriada = objectMapper.convertValue(companhiaRepository.create(companhia),
                    CompanhiaDTO.class);
            companhiaCriada.setAtivo(usuarioCriado.isAtivo());

            return companhiaCriada;

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public CompanhiaDTO update(Integer id, CompanhiaCreateDTO companhiaDTO) throws RegraDeNegocioException {
        try {
            Companhia companhia = companhiaRepository.getById(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Companhia não existe."));

            Usuario usuario = new Usuario(
                    companhia.getIdUsuario(),
                    companhiaDTO.getLogin(),
                    companhiaDTO.getSenha(),
                    companhiaDTO.getNome(),
                    TipoUsuario.COMPANHIA,
                    true);

            usuarioService.update(companhia.getIdUsuario(), usuario);

            Companhia companhiaEditada = objectMapper.convertValue(companhiaDTO, Companhia.class);

            if(companhiaRepository.update(id, companhiaEditada)) {
                companhiaEditada.setIdCompanhia(id);
                companhiaEditada.setAtivo(companhia.isAtivo());

                return objectMapper.convertValue(companhiaEditada, CompanhiaDTO.class);

            } else {
                throw new RegraDeNegocioException("Companhia não pode ser editada.");
            }
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public void delete(Integer idCompanhia) throws RegraDeNegocioException {
        try {
            Companhia companhiaEncontrada = companhiaRepository.getById(idCompanhia)
                    .orElseThrow(() -> new RegraDeNegocioException("Companhia não encontrada!"));

            usuarioService.delete(companhiaEncontrada.getIdUsuario());

        }catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public CompanhiaDTO getById(Integer id) throws RegraDeNegocioException {
        try {
            Companhia companhia = companhiaRepository.getById(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Companhia não encontrada"));

            return objectMapper.convertValue(companhia, CompanhiaDTO.class);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a recuperação da companhia.");
        }
    }

    public Companhia getByNome(String nome) throws RegraDeNegocioException {
        try {
            return companhiaRepository.getByNome(nome)
                    .orElseThrow(() -> new RegraDeNegocioException("Companhia não Encontrada"));

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a recuperação da companhia.");
        }
    }
}
