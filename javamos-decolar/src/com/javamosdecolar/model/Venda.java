package javamos_decolar.com.javamosdecolar.model;
import java.time.LocalDateTime;

public class Venda {
    private int idVenda;
    private String codigo;
    private Passagem passagem;
    private Comprador comprador;
    private Companhia companhia;
    private LocalDateTime data;
    private Status status;

    public Venda(String codigo, Passagem passagem, Comprador comprador,
                 Companhia companhia, LocalDateTime data, Status status) {
        this.codigo = codigo;
        this.passagem = passagem;
        this.comprador = comprador;
        this.companhia = companhia;
        this.data = data;
        this.status = status;
    }

    public Venda () {}

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Passagem getPassagem() {
        return passagem;
    }

    public void setPassagem(Passagem passagem) {
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

    public void setCompanhia(Companhia companhia) {
        this.companhia = companhia;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "ID='" + idVenda + '\'' +
                "codigo='" + codigo + '\'' +
                ", passagemId=" + passagem.getIdPassagem() +
                ", comprador=" + comprador.getNome() +
                ", companhia=" + companhia.getNome() +
                ", data=" + data +
                ", status=" + status +
                '}';
    }
}
