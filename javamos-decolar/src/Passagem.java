package javamos_decolar;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Passagem {
    private String codigo;
    private LocalDate dataPartida;
    private LocalDate dataChegada;
    private Trecho trecho;
    private boolean disponivel;

    public Passagem(LocalDate dataPartida, LocalDate dataChegada,
                    Trecho trecho, boolean disponivel, BigDecimal valor) {
        this.codigo = this.geraCodigoPassagem();
        this.dataPartida = dataPartida;
        this.dataChegada = dataChegada;
        this.trecho = trecho;
        this.disponivel = disponivel;
        this.valor = valor;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public LocalDate getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(LocalDate dataPartida) {
        this.dataPartida = dataPartida;
    }

    public LocalDate getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(LocalDate dataChegada) {
        this.dataChegada = dataChegada;
    }

    public Trecho getTrecho() {
        return trecho;
    }

    public void setTrecho(Trecho trecho) {
        this.trecho = trecho;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    private BigDecimal valor;

    @Override
    public String toString() {
        return "Passagem{" +
                "codigo='" + codigo + '\'' +
                ", dataPartida=" + dataPartida +
                ", dataChegada=" + dataChegada +
                ", trecho=" + trecho +
                ", disponivel=" + disponivel +
                ", valor=" + valor +
                '}';
    }

    public String geraCodigoPassagem() {
        return String.valueOf(1 + (int) (Math.random() * 2000));
    }
}
