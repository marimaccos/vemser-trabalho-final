package br.com.dbc.javamosdecolar.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class Passagem {
    private int idPassagem;
    private String codigo;
    private LocalDateTime dataPartida;
    private LocalDateTime dataChegada;
    private boolean disponivel;
    private BigDecimal valor;
    private Trecho trecho;

    public Passagem(String codigo, LocalDateTime dataPartida, LocalDateTime dataChegada,
                    Trecho trecho, boolean disponivel, BigDecimal valor) {
        this.codigo = codigo;
        this.dataPartida = dataPartida;
        this.dataChegada = dataChegada;
        this.trecho = trecho;
        this.disponivel = disponivel;
        this.valor = valor;
    }

    public Passagem(LocalDateTime dataPartida, LocalDateTime dataChegada, BigDecimal valor) {
        this.dataPartida = dataPartida;
        this.dataChegada = dataChegada;
        this.valor = valor;
        this.disponivel = true;
    }

    public Passagem(String codigo, LocalDateTime dataPartida, LocalDateTime dataChegada,
                    boolean disponivel, BigDecimal valor) {
        this.codigo = codigo;
        this.dataPartida = dataPartida;
        this.dataChegada = dataChegada;
        this.disponivel = disponivel;
        this.valor = valor;
    }
}
