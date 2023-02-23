package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.Companhia;
import javamos_decolar.com.javamosdecolar.model.Passagem;
import javamos_decolar.com.javamosdecolar.model.Trecho;
import javamos_decolar.com.javamosdecolar.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PassagemService {

    private PassagemRepository passagemRepository;
    private TrechoRepository trechoRepository;
    private CompanhiaRepository companhiaRepository;

    public PassagemService() {
        passagemRepository = new PassagemRepository();
    }

    public void cadastrarPassagem(String trecho, PassagemRepository passagemDados, LocalDate dataPartida,
                                  LocalDate dataChegada, BigDecimal valor, Companhia companhia) {
        String[] origemEDestino = trecho.split("/");

        Optional<Trecho> trechoOptional = trechoRepository.buscarTrecho(origemEDestino[0],
                origemEDestino[1]);

        if (trechoOptional.isPresent()) {
            Passagem passagem = new Passagem(dataPartida, dataChegada,
                    trechoOptional.get(), true, valor);
            passagemDados.adicionar(passagem);
            this.getPassagensCadastradas().add(passagem);
            System.out.println("Passagem adicionada com sucesso!");
        } else {
            System.err.println("Trecho inválido!");
        }
    }

    public void listarPassagemPorData(LocalDate data, int tipoDeData) {
        try {
            if(tipoDeData == 1) {
                passagemRepository.pegarPassagemPorDataPartida(data).stream()
                        .forEach(System.out::println);
            } else if (tipoDeData == 2) {
                passagemRepository.pegarPassagemPorDataChegada(data).stream()
                        .forEach(System.out::println);
            } else {
                throw new Exception("Opção inválida!");
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }

    public void listarPassagemPorValorMaximo(BigDecimal valorMaximo) {
        try {
            passagemRepository.pegarPassagemPorValor(valorMaximo).stream().forEach(System.out::println);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void listarPassagemPorCompanhia(String nomeCompanhia) {
        try {
            Optional<Companhia> companhiaEncontrada = companhiaRepository.buscaCompanhiaPorNome(nomeCompanhia);

            if(companhiaEncontrada.isEmpty()) {
                throw new Exception("Companhia não encontrada!");
            }

            List<Passagem> passagems = passagemRepository
                    .pegarPassagemPorCompanhia(companhiaEncontrada.get().getIdCompanhia());

            passagems.stream().forEach(System.out::println);

        } catch (DatabaseException e) {
            e.printStackTrace();
        }  catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }

}
