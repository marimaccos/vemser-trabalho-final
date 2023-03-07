package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.dto.TrechoCreateDTO;
import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Companhia;
import br.com.dbc.javamosdecolar.model.Trecho;
import br.com.dbc.javamosdecolar.model.Usuario;
import br.com.dbc.javamosdecolar.dto.TrechoDTO;
import br.com.dbc.javamosdecolar.repository.TrechoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrechoService {

    private final TrechoRepository trechoRepository;
    private final CompanhiaService companhiaService;
    private final ObjectMapper objectMapper;

    public TrechoDTO criarTrecho(TrechoCreateDTO trecho) throws RegraDeNegocioException {
        try {
            companhiaService.getCompanhiaById(trecho.getIdCompanhia());

            Trecho trechoNovo = objectMapper.convertValue(trecho, Trecho.class);
            Trecho trechoCriado = trechoRepository.adicionar(trechoNovo);

            return objectMapper.convertValue(trechoCriado, TrechoDTO.class);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public TrechoDTO editarTrecho(Integer idTrecho, TrechoCreateDTO trecho) throws RegraDeNegocioException {
        try {
            companhiaService.getCompanhiaById(trecho.getIdCompanhia());

            Trecho trechoEncontrado = trechoRepository.getTrechoPorId(idTrecho)
                    .orElseThrow(() -> new RegraDeNegocioException("Trecho não encontrado!"));

            if (trechoEncontrado.getCompanhia().getIdCompanhia().equals(trecho.getIdCompanhia())) {
                Trecho trechoNovo = objectMapper.convertValue(trecho, Trecho.class);

                trechoRepository.editar(idTrecho, trechoNovo);

                return objectMapper.convertValue(trechoEncontrado, TrechoDTO.class);

            } else {
                throw new RegraDeNegocioException("Trecho não é da companhia!");
            }

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }

    }

    public List<TrechoDTO> listaTrechos() throws RegraDeNegocioException {
        try {
            List<TrechoDTO> listaTrechos = trechoRepository.listar().stream()
                    .map(trecho -> objectMapper.convertValue(trecho, TrechoDTO.class))
                    .toList();

            return listaTrechos;

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public void deletarTrecho(Integer idTrecho, Usuario usuario) throws RegraDeNegocioException {
        //TO-DO implementar
    }

    public TrechoDTO getTrechoById(Integer idTrecho) throws RegraDeNegocioException {
        try {
            Trecho trecho = trechoRepository.getTrechoPorId(idTrecho)
                    .orElseThrow(() -> new RegraDeNegocioException("Aconteceu algum problema durante a listagem."));
            return objectMapper.convertValue(trecho, TrechoDTO.class);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }
}
