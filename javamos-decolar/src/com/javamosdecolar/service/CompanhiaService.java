package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.model.Passagem;
import javamos_decolar.com.javamosdecolar.model.Trecho;
import javamos_decolar.com.javamosdecolar.repository.CompanhiaRepository;
import javamos_decolar.com.javamosdecolar.repository.PassagemRepository;
import javamos_decolar.com.javamosdecolar.repository.TrechoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class CompanhiaService implements Historico{

    private CompanhiaRepository companhiaRepository;

    public CompanhiaService() {
        companhiaRepository = new CompanhiaRepository();
    }

    @Override
    public void imprimirHistorico() {
        historicoVendas.stream().forEach(System.out::println);
    }

    public void cadastrarPassagem(String trecho, PassagemRepository passagemDados,
                                  TrechoRepository trechoDados, LocalDate dataPartida,
                                  LocalDate dataChegada, BigDecimal valor) {
        String[] origemEDestino = trecho.split("/");

        Optional<Trecho> trechoOptional = trechoDados.buscarTrecho(origemEDestino[0],
                origemEDestino[1], this);

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
