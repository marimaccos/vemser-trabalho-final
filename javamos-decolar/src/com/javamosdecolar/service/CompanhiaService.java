package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.exceptions.RegraDeNegocioException;
import javamos_decolar.com.javamosdecolar.model.Companhia;
import javamos_decolar.com.javamosdecolar.model.Passagem;
import javamos_decolar.com.javamosdecolar.model.Usuario;
import javamos_decolar.com.javamosdecolar.repository.CompanhiaRepository;
import javamos_decolar.com.javamosdecolar.repository.PassagemRepository;
import javamos_decolar.com.javamosdecolar.repository.TrechoRepository;
import javamos_decolar.com.javamosdecolar.repository.VendaRepository;

import java.util.Optional;

public class CompanhiaService {

    private CompanhiaRepository companhiaRepository;
    private VendaRepository vendaRepository;
    private PassagemRepository passagemRepository;
    private TrechoRepository trechoRepository;

    public CompanhiaService() {
        companhiaRepository = new CompanhiaRepository();
        vendaRepository = new VendaRepository();
        passagemRepository = new PassagemRepository();
        trechoRepository = new TrechoRepository();
    }

    public void imprimirTrechosDaCompanhia(Usuario usuario) throws RegraDeNegocioException {
        try {
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia não pode ser encontrada!");
            }

            trechoRepository.getTrechosPorCompanhia(companhia.get().getIdCompanhia())
                    .stream().forEach(System.out::println);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public void imprimirHistoricoDeVendas(Usuario usuario) throws RegraDeNegocioException {
        try {
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia não pode ser encontrada!");
            }

            vendaRepository.buscarVendasPorCompanhia(companhia.get().getIdCompanhia())
                    .stream().forEach(System.out::println);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }


    public void listarPassagensCadastradas(Usuario usuario) throws RegraDeNegocioException {
        try{
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia não pode ser encontrada!");
            }

            passagemRepository.getPassagemPorCompanhia(companhia.get().getIdCompanhia())
                    .stream().forEach(System.out::println);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public boolean deletarPassagem(Integer indexRemocaoPassagem, Usuario usuario) throws RegraDeNegocioException {
        try{
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia não pode ser encontrada!");
            }

            Optional<Passagem> passagem = passagemRepository.getPassagemPeloId(indexRemocaoPassagem);

            boolean companhiaEhDonaDaPassagem = passagem.get().getTrecho().getCompanhia().equals(companhia.get());

            if(!companhiaEhDonaDaPassagem) {
                throw new RegraDeNegocioException("Permissão negada! Passagem não pode ser deletada!");
            }

            passagemRepository.remover(indexRemocaoPassagem);
            return true;
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }
}
