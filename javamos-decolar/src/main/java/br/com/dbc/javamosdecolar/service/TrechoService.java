package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.dto.TrechoCreateDTO;
import br.com.dbc.javamosdecolar.dto.TrechoDTO;
import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Companhia;
import br.com.dbc.javamosdecolar.model.Trecho;
import br.com.dbc.javamosdecolar.repository.TrechoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrechoService {

    private final TrechoRepository trechoRepository;
    private final CompanhiaService companhiaService;
    private final ObjectMapper objectMapper;

    public TrechoDTO criarTrecho(TrechoCreateDTO trechoDTO) throws RegraDeNegocioException {
        try {
            Companhia companhia = objectMapper
                    .convertValue(companhiaService.getCompanhiaById(trechoDTO.getIdCompanhia()),
                            Companhia.class);

            if(!companhia.isAtivo()) {
                throw new RegraDeNegocioException("Companhia indisponível.");
            }

            // Checa se a companhia já cadastrou esse trecho
            if(trechoRepository.getTrecho(trechoDTO.getOrigem().toUpperCase(),
                    trechoDTO.getDestino().toUpperCase(), companhia).isPresent()) {
                throw new RegraDeNegocioException("Trecho já existe!");
            }
            Trecho trecho = objectMapper.convertValue(trechoDTO, Trecho.class);

            trecho.setCompanhia(companhia);

            TrechoDTO trechoNovo = objectMapper
                    .convertValue(trechoRepository.adicionar(trecho), TrechoDTO.class);

            trechoNovo.setIdCompanhia(companhia.getIdCompanhia());

            return trechoNovo;

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public TrechoDTO editarTrecho(Integer idTrecho, TrechoCreateDTO trechoDTO) throws RegraDeNegocioException {
        try {
            trechoRepository.getTrechoPorId(idTrecho)
                    .orElseThrow(() -> new RegraDeNegocioException("Trecho não encontrado!"));

            Companhia companhia = objectMapper
                    .convertValue(companhiaService.getCompanhiaById(trechoDTO.getIdCompanhia()), Companhia.class);

            if(trechoRepository.getTrecho(trechoDTO.getOrigem().toUpperCase(),
                    trechoDTO.getDestino().toUpperCase(), companhia).isPresent()) {
                throw new RegraDeNegocioException("Trecho já existe!");
            }

            Trecho trechoEditado = objectMapper.convertValue(trechoDTO, Trecho.class);
            trechoEditado.setIdTrecho(idTrecho);

            if(trechoRepository.editar(idTrecho, trechoEditado)){
                TrechoDTO trechoEditadoDTO = objectMapper.convertValue(trechoEditado, TrechoDTO.class);
                trechoEditadoDTO.setIdCompanhia(companhia.getIdCompanhia());

                return trechoEditadoDTO;

            } else {
                throw new RegraDeNegocioException("Não foi possível completar a edição.");
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void deletarTrecho(Integer idTrecho) throws RegraDeNegocioException {
        try {
            trechoRepository.getTrechoPorId(idTrecho)
                    .orElseThrow(() -> new RegraDeNegocioException("Trecho não encontrado!"));

            trechoRepository.remover(idTrecho);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão.");
        }
    }

    public List<TrechoDTO> listaTrechos() throws RegraDeNegocioException {
        try {
            List<TrechoDTO> listaTrechos = trechoRepository.listar().stream()
                    .map(trecho -> {
                        TrechoDTO trechoDTO = objectMapper.convertValue(trecho, TrechoDTO.class);
                        trechoDTO.setIdCompanhia(trecho.getCompanhia().getIdCompanhia());
                        return trechoDTO;
                    })
                    .toList();

            return listaTrechos;

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public List<TrechoDTO> getTrechosPorCompanhia(Integer idCompanhia) throws RegraDeNegocioException {
        try {
            // Checa se companhia existe
            companhiaService.getCompanhiaById(idCompanhia);

            return trechoRepository.getTrechosPorCompanhia(idCompanhia).stream()
                    .map(trecho -> objectMapper.convertValue(trecho, TrechoDTO.class))
                    .toList();

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public TrechoDTO getTrechoById(Integer idTrecho) throws RegraDeNegocioException {
        try {
            Trecho trecho = trechoRepository.getTrechoPorId(idTrecho)
                    .orElseThrow(() -> new RegraDeNegocioException("Aconteceu algum problema durante a listagem."));

            TrechoDTO trechoDTO = objectMapper.convertValue(trecho, TrechoDTO.class);
            trechoDTO.setIdCompanhia(trecho.getCompanhia().getIdCompanhia());

            return trechoDTO;

        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }
}
