package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Trecho;
import br.com.dbc.javamosdecolar.model.Usuario;
import br.com.dbc.javamosdecolar.dto.TrechoDTO;
import br.com.dbc.javamosdecolar.repository.TrechoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrechoService {

    private final TrechoRepository trechoRepository;

    public void criarTrecho(Trecho novoTrecho, Usuario usuario) throws RegraDeNegocioException {
        try {
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia não pode ser encontrada!");
            }

            if(novoTrecho.getOrigem().length() > 3 || novoTrecho.getDestino().length() > 3) {
                throw new RegraDeNegocioException("Origem e Destino só podem ter três caracteres!");
            }

            Optional<Trecho> trechoJaCadastrado = trechoRepository.getTrecho(novoTrecho.getOrigem(),
                    novoTrecho.getDestino(), companhia.get());

            if(trechoJaCadastrado.isPresent()) {
                throw new RegraDeNegocioException("Trecho já existente!");
            }

            novoTrecho.setCompanhia(companhia.get());
            Trecho trechoAdicionado = trechoRepository.adicionar(novoTrecho);
            System.out.println("Trecho adicinado com sucesso! " + trechoAdicionado);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public void editarTrecho(Integer idTrecho, Trecho novoTrecho, Usuario usuario) throws RegraDeNegocioException {
        try {
            if(novoTrecho.getOrigem().length() > 3 || novoTrecho.getDestino().length() > 3) {
                throw new RegraDeNegocioException("Origem e Destino só podem ter três caracteres!");
            }

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
                throw new RegraDeNegocioException("Permissão negada! Trecho não pode ser editado!");
            }

            novoTrecho.setCompanhia(companhia.get());

            boolean conseguiuEditar = trechoRepository.editar(idTrecho, novoTrecho);
            System.out.println("Conseguiu Editar? " + conseguiuEditar + "| com id=" + idTrecho);

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

    public List<TrechoDTO> listaTrechos() {

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
