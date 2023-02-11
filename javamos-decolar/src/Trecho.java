package javamos_decolar;

public class Trecho {

    private String origem;
    private String destino;
    private Companhia companhia;

    public Trecho(String origem, String destino, Companhia companhia) {
        this.origem = origem;
        this.destino = destino;
        this.companhia = companhia;
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

    public void imprimirTrecho() {
        System.out.printf(
                "Origem: %s%nDestino: %s%nCompanhia: %s",
                getOrigem(), getDestino(), getCompanhia().getNome()
        );
    }
}
