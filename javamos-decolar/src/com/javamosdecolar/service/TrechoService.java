package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.exceptions.RegraDeNegocioException;
import javamos_decolar.com.javamosdecolar.model.Companhia;
import javamos_decolar.com.javamosdecolar.model.Trecho;
import javamos_decolar.com.javamosdecolar.model.Usuario;
import javamos_decolar.com.javamosdecolar.repository.CompanhiaRepository;
import javamos_decolar.com.javamosdecolar.repository.TrechoRepository;

import java.util.Optional;

public class TrechoService {

    private TrechoRepository trechoRepository;
    private CompanhiaRepository companhiaRepository;

    public TrechoService() {
        trechoRepository = new TrechoRepository();
        companhiaRepository = new CompanhiaRepository();
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
}
