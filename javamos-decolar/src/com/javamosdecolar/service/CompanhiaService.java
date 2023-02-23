package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.Companhia;
import javamos_decolar.com.javamosdecolar.model.Passagem;
import javamos_decolar.com.javamosdecolar.model.Trecho;
import javamos_decolar.com.javamosdecolar.model.Usuario;
import javamos_decolar.com.javamosdecolar.repository.CompanhiaRepository;
import javamos_decolar.com.javamosdecolar.repository.PassagemRepository;
import javamos_decolar.com.javamosdecolar.repository.TrechoRepository;
import javamos_decolar.com.javamosdecolar.repository.VendaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CompanhiaService {

    private CompanhiaRepository companhiaRepository;
    private VendaRepository vendaRepository;
    private TrechoRepository trechoRepository;
    private PassagemRepository passagemRepository;

    public CompanhiaService() {
        companhiaRepository = new CompanhiaRepository();
    }

    public void cadastrarPassagem(String trecho, PassagemRepository passagemDados,
                                  TrechoRepository trechoDados, LocalDate dataPartida,
                                  LocalDate dataChegada, BigDecimal valor) {
        String[] origemEDestino = trecho.split("/");

//        Optional<Trecho> trechoOptional = trechoDados.buscarTrecho(origemEDestino[0],
//                origemEDestino[1], this);

//        if (trechoOptional.isPresent()) {
//            Passagem passagem = new Passagem(dataPartida, dataChegada,
//                    trechoOptional.get(), true, valor);
//            passagemDados.adicionar(passagem);
//            this.getPassagensCadastradas().add(passagem);
//            System.out.println("Passagem adicionada com sucesso!");
//        } else {
//            System.err.println("Trecho inválido!");
//        }
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

    public void deletarTrecho(Integer idTrecho, Usuario usuario) {
        try {
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new Exception("Companhia não pode ser encontrada!");
            }

            Optional<Trecho> trecho = trechoRepository.buscarTrechoPorId(idTrecho);

            if(trecho.isEmpty()) {
                throw new Exception("Trecho não pode ser encontrado!");
            }

            boolean trechoEhDaMesmaCompanhia =
                    trecho.get().getCompanhia().getIdCompanhia() == companhia.get().getIdCompanhia();

            if(!trechoEhDaMesmaCompanhia) {
                throw new Exception("Permissão negada! Trecho não pode ser deletado!");
            }

            trechoRepository.remover(idTrecho);

        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }

    public void editarTrecho(Integer idTrecho, Trecho novoTrecho, Usuario usuario) {
        try {
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new Exception("Companhia não pode ser encontrada!");
            }

            Optional<Trecho> trecho = trechoRepository.buscarTrechoPorId(idTrecho);

            if(trecho.isEmpty()) {
                throw new Exception("Trecho não pode ser encontrado!");
            }

            boolean trechoEhDaMesmaCompanhia =
                    trecho.get().getCompanhia().getIdCompanhia() == companhia.get().getIdCompanhia();

            if(!trechoEhDaMesmaCompanhia) {
                throw new Exception("Permissão negada! Trecho não pode ser editado!");
            }

            novoTrecho.setCompanhia(companhia.get());

            trechoRepository.editar(idTrecho, novoTrecho);

        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }

    }


    public void criarTrecho(Trecho novoTrecho, Usuario usuario) {
        try {
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new Exception("Companhia não pode ser encontrada!");
            }

            Optional<Trecho> trechoJaCadastrado = trechoRepository.buscarTrecho(novoTrecho.getOrigem(),
                    novoTrecho.getDestino(), companhia.get());

            if(trechoJaCadastrado.isPresent()) {
                throw new Exception("Trecho já existente!");
            }

            novoTrecho.setCompanhia(companhia.get());
            trechoRepository.adicionar(novoTrecho);
            
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
