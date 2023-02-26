package javamos_decolar.com.javamosdecolar.model;

public class Trecho {
    private int idTrecho;
    private String origem;
    private String destino;
    private Companhia companhia;

    public Trecho() {
    }

    public Trecho(int idTrecho, String origem, String destino) {
        this.idTrecho = idTrecho;
        this.origem = origem;
        this.destino = destino;
    }

    public Trecho(String origem, String destino) {
        this.origem = origem;
        this.destino = destino;
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
                ", origem='" + origem + '\'' +
                ", destino='" + destino + '\'' +
                ", ID companhia=" + companhia.getIdCompanhia() +
                ", nome companhia=" + companhia.getNomeFantasia() +
                '}';
    }
}
