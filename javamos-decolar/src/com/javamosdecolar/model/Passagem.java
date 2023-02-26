package javamos_decolar.com.javamosdecolar.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Passagem {
    private int idPassagem;
    private String codigo;
    private LocalDateTime dataPartida;
    private LocalDateTime dataChegada;
    private boolean disponivel;
    private BigDecimal valor;
    private Trecho trecho;

    public Passagem() {
    }

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

    public LocalDateTime getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(LocalDateTime dataPartida) {
        this.dataPartida = dataPartida;
    }

    public LocalDateTime getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(LocalDateTime dataChegada) {
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

    @Override
    public String toString() {
        return "Passagem{" +
                "ID='" + idPassagem + '\'' +
                ", codigo='" + codigo + '\'' +
                ", dataPartida=" + dataPartida +
                ", dataChegada=" + dataChegada +
                ", trecho=" + trecho +
                ", disponivel=" + disponivel +
                ", valor=" + valor +
                '}';
    }

}
