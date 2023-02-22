package javamos_decolar.com.javamosdecolar.model;

import javamos_decolar.com.javamosdecolar.model.Companhia;

public class Trecho {
    private int idTrecho;
    private String origem;
    private String destino;
    private Companhia companhia;

    public Trecho(String origem, String destino, Companhia companhia) {
        this.origem = origem;
        this.destino = destino;
        this.companhia = companhia;
    }

    public int getIdTrecho() {
        return idTrecho;
    }

    public void setIdTrecho(int idTrecho) {
        this.idTrecho = idTrecho;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Companhia getCompanhia() {
        return companhia;
    }

    public void setCompanhia(Companhia companhia) {
        this.companhia = companhia;
    }

    @Override
    public String toString() {
        return "Trecho{" +
                "ID='" + idTrecho + '\'' +
                "origem='" + origem + '\'' +
                ", destino='" + destino + '\'' +
                ", companhia=" + companhia.getNome() +
                '}';
    }
}
