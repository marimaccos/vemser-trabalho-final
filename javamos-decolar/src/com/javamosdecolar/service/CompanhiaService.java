package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.Companhia;
import javamos_decolar.com.javamosdecolar.model.Passagem;
import javamos_decolar.com.javamosdecolar.model.Usuario;
import javamos_decolar.com.javamosdecolar.repository.CompanhiaRepository;
import javamos_decolar.com.javamosdecolar.repository.PassagemRepository;
import javamos_decolar.com.javamosdecolar.repository.VendaRepository;

import java.util.Optional;

public class CompanhiaService {

    private CompanhiaRepository companhiaRepository;
    private VendaRepository vendaRepository;
    private PassagemRepository passagemRepository;

    public CompanhiaService() {
        companhiaRepository = new CompanhiaRepository();
        vendaRepository = new VendaRepository();
        passagemRepository = new PassagemRepository();
    }

    public void imprimirTrechosDaCompanhia(Usuario usuario) {
        try {
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new Exception("Companhia não pode ser encontrada!");
            }

            vendaRepository.buscarTrechosPorCompanhia(companhia.get().getIdCompanhia())
                    .stream().forEach(System.out::println);

        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }

    public void imprimirHistoricoDeVendas(Usuario usuario) {
        try {
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new Exception("Companhia não pode ser encontrada!");
            }

            vendaRepository.buscarVendasPorCompanhia(companhia.get().getIdCompanhia())
                    .stream().forEach(System.out::println);

        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }


    public void listarPassagensCadastradas(Usuario usuario) {
        try{
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new Exception("Companhia não pode ser encontrada!");
            }

            passagemRepository.pegarPassagemPorCompanhia(companhia.get().getIdCompanhia())
                    .stream().forEach(System.out::println);

        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deletarPassagem(Integer indexRemocaoPassagem, Usuario usuario) {
        try{
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new Exception("Companhia não pode ser encontrada!");
            }

            Optional<Passagem> passagem = passagemRepository.pegarPassagemPeloId(indexRemocaoPassagem);

            boolean companhiaEhDonaDaPassagem = passagem.get().getTrecho().getCompanhia().equals(companhia.get());

            if(!companhiaEhDonaDaPassagem) {
                throw new Exception("Permissão negada! Passagem não pode ser deletada!");
            }

            passagemRepository.remover(indexRemocaoPassagem);
            return true;
        } catch (DatabaseException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
