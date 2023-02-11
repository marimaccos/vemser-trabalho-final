package javamos_decolar;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Venda {
    private String codigo;
    private BigDecimal valor;
    private Passagem passagem;
    private Comprador comprador;
    private Companhia companhia;
    private LocalDate data;
    private Status status;


    public Venda(String codigo, BigDecimal valor, Passagem passagem,
                 Comprador comprador, Companhia companhia, LocalDate data,
                 Status status) {
        this.codigo = codigo;
        this.valor = valor;
        this.passagem = passagem;
        this.comprador = comprador;
        this.companhia = companhia;
        this.data = data;
        this.status = status;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Passagem getPassagem() {
        return passagem;
    }

    public void setPassagens(Passagem passagem) {
        this.passagem = passagem;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public Companhia getCompanhia() {
        return companhia;
    }

    public void setCompanhia(Companhia compranhia) {
        this.companhia = compranhia;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void imprimirPassagem() {
             System.out.println(passagem);
    }

    @Override
    public String toString() {
        return "Venda{" +
                "codigo='" + codigo + '\'' +
                ", valor=" + valor +
                ", passagens=" + passagem +
                ", comprador=" + comprador +
                ", compranhia=" + companhia +
                ", data=" + data +
                ", status=" + status +
                '}';
    }
}
