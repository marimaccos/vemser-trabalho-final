package br.com.dbc.javamosdecolar.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreatePassagemDTO {
    private LocalDateTime dataPartida;
    private LocalDateTime dataChegada;
    private BigDecimal valor;
    private Integer trechoId;

    public CreatePassagemDTO() {
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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getTrechoId() {
        return trechoId;
    }

    public void setTrechoId(Integer trechoId) {
        this.trechoId = trechoId;
    }
}
