package javamos_decolar;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Venda {
    private String codigo;
    private BigDecimal valor;
    private List<Passagem> passagens;
    private Comprador comprador;
    private Companhia companhia;
    private LocalDate data;
    private Status status;

    public Venda(String codigo, BigDecimal valor, List<Passagem> passagens,
                 Comprador comprador, Companhia companhia, LocalDate data,
                 Status status) {
        this.codigo = codigo;
        this.valor = valor;
        this.passagens = passagens;
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

    public List<Passagem> getPassagens() {
        return passagens;
    }

    public void setPassagens(List<Passagem> passagens) {
        this.passagens = passagens;
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
        for (Passagem passagem :
                passagens) {
            System.out.println(passagem);
        }
    }

    @Override
    public String toString() {
        return "Venda{" +
                "codigo='" + codigo + '\'' +
                ", valor=" + valor +
                ", passagens=" + passagens +
                ", comprador=" + comprador +
                ", compranhia=" + companhia +
                ", data=" + data +
                ", status=" + status +
                '}';
    }
}
