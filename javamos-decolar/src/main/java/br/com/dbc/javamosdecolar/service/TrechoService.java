package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.dto.TrechoCreateDTO;
import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Trecho;
import br.com.dbc.javamosdecolar.model.Usuario;
import br.com.dbc.javamosdecolar.dto.TrechoDTO;
import br.com.dbc.javamosdecolar.repository.TrechoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
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

    public void deletarTrecho(Integer idTrecho, Usuario usuario) throws RegraDeNegocioException {
        try {
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia não pode ser encontrada!");
            }

            Optional<Trecho> trecho = trechoRepository.getTrechoPorId(idTrecho);

            if(trecho.isEmpty()) {
                throw new RegraDeNegocioException("Trecho não pode ser encontrado!");
            }

            boolean trechoEhDaMesmaCompanhia =
                    trecho.get().getCompanhia().getIdCompanhia() == companhia.get().getIdCompanhia();

            if(!trechoEhDaMesmaCompanhia) {
                throw new RegraDeNegocioException("Trecho não pode ser deletado!");
            }

            boolean conseguiuRemover = trechoRepository.remover(idTrecho);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + idTrecho);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Trecho não pode ser deletado!");
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

    public void imprimirTrechosDaCompanhia(Usuario usuario) throws RegraDeNegocioException {
        try {
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia não pode ser encontrada!");
            }

            List<Trecho> trechosPorCompanhia = trechoRepository.getTrechosPorCompanhia(companhia.get()
                    .getIdCompanhia());

            if(trechosPorCompanhia.isEmpty()) {
                System.out.println("Não há trechos cadastrados!");
            } else {
                trechosPorCompanhia.stream().forEach(System.out::println);
            }

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

}
