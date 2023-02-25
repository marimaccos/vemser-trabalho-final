package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.exceptions.RegraDeNegocioException;
import javamos_decolar.com.javamosdecolar.model.*;
import javamos_decolar.com.javamosdecolar.repository.CompanhiaRepository;
import javamos_decolar.com.javamosdecolar.repository.PassagemRepository;
import javamos_decolar.com.javamosdecolar.repository.TrechoRepository;
import javamos_decolar.com.javamosdecolar.repository.VendaRepository;

import java.util.List;
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

    public void imprimirHistoricoDeVendas(Usuario usuario) throws RegraDeNegocioException {
        try {
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia não pode ser encontrada!");
            }

            List<Venda> vendasPorCompanhia = vendaRepository.getVendasPorCompanhia(companhia.get().getIdCompanhia());

            if (vendasPorCompanhia.isEmpty()) {
                System.out.println("Não há nada para exibir.");
            } else {
                vendasPorCompanhia.stream().forEach(System.out::println);
            }

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

            List<Passagem> passagemPorCompanhia = passagemRepository
                    .getPassagemPorCompanhia(companhia.get().getIdCompanhia());

            if(passagemPorCompanhia.isEmpty()) {
                System.out.println("Não há passagens cadastradas!");
            } else {
                passagemPorCompanhia.stream().forEach(System.out::println);
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public void deletarPassagem(Integer indexRemocaoPassagem, Usuario usuario) throws RegraDeNegocioException {
        try{
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia não pode ser encontrada!");
            }

            Optional<Passagem> passagem = passagemRepository.getPassagemPeloId(indexRemocaoPassagem);

            boolean companhiaEhDonaDaPassagem = passagem.get().getTrecho().getCompanhia().getIdCompanhia()
                    .equals(companhia.get().getIdCompanhia());

            if(!companhiaEhDonaDaPassagem) {
                throw new RegraDeNegocioException("Passagem não pode ser deletada!");
            }

            boolean conseguiuRemover = passagemRepository.remover(indexRemocaoPassagem);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + indexRemocaoPassagem);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }
}
