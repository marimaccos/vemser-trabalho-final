package javamos_decolar.com.javamosdecolar.model;

import javamos_decolar.com.javamosdecolar.utils.Codigo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Passagem {
    private int idPassagem;
    private String codigo;
    private LocalDate dataPartida;
    private LocalDate dataChegada;
    private Trecho trecho;
    private boolean disponivel;

    public Passagem(String codigo, LocalDate dataPartida, LocalDate dataChegada,
                    Trecho trecho, boolean disponivel, BigDecimal valor) {
        this.codigo = codigo;
        this.dataPartida = dataPartida;
        this.dataChegada = dataChegada;
        this.trecho = trecho;
        this.disponivel = disponivel;
        this.valor = valor;
    }

    public Passagem(LocalDate dataPartida, LocalDate dataChegada, BigDecimal valor) {
        this.dataPartida = dataPartida;
        this.dataChegada = dataChegada;
        this.valor = valor;
        this.disponivel = true;
    }

    public Passagem(String codigo, LocalDate dataPartida, LocalDate dataChegada, boolean disponivel, BigDecimal valor) {
        this.codigo = codigo;
        this.dataPartida = dataPartida;
        this.dataChegada = dataChegada;
        this.disponivel = disponivel;
        this.valor = valor;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public int getIdPassagem() {
        return idPassagem;
    }

    public void setIdPassagem(int idPassagem) {
        this.idPassagem = idPassagem;
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
                "ID='" + idPassagem + '\'' +
                "codigo='" + codigo + '\'' +
                ", dataPartida=" + dataPartida +
                ", dataChegada=" + dataChegada +
                ", trecho=" + trecho +
                ", disponivel=" + disponivel +
                ", valor=" + valor +
                '}';
    }

}
