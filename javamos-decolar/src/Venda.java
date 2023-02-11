package javamos_decolar;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Venda {
    private String codigo;
    private Passagem passagem;
    private Comprador comprador;
    private Companhia companhia;
    private LocalDate data;
    private Status status;

    public Venda() {
    }

    public Venda(Passagem passagem, Comprador comprador,
                 Companhia companhia, LocalDate data, Status status) {
        this.codigo = this.geraCodigoVenda();
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

    public void imprimirPassagemComprada() {
             System.out.println(passagem);
    }

    public Venda efetuarVenda(Passagem passagem, Comprador comprador,
                                Companhia companhia, VendaDados vendaDados) {

        Venda vendaAtual = new Venda(passagem, comprador,
                companhia, LocalDate.now(), Status.CONCLUIDO);
        vendaDados.adicionar(vendaAtual);
        comprador.getHistoricoCompras().add(vendaAtual);
        companhia.getHistoricoVendas().add(vendaAtual);
        passagem.setDisponivel(false);

        return vendaAtual;
    }

    public String geraCodigoVenda() {
        return String.valueOf(1 + (int) (Math.random() * 2000));
    }

    @Override
    public String toString() {
        return "Venda{" +
                "codigo='" + codigo + '\'' +
                ", passagem=" + passagem +
                ", comprador=" + comprador.getNome() +
                ", companhia=" + companhia.getNome() +
                ", data=" + data +
                ", status=" + status +
                '}';
    }
}
