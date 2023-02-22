package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.model.Companhia;
import javamos_decolar.com.javamosdecolar.model.Passagem;
import javamos_decolar.com.javamosdecolar.model.Trecho;
import javamos_decolar.com.javamosdecolar.repository.PassagemRepository;
import javamos_decolar.com.javamosdecolar.repository.Repository;
import javamos_decolar.com.javamosdecolar.repository.TrechoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class PassagemService {

    private PassagemRepository passagemRepository;
    private TrechoRepository trechoRepository;

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

}
