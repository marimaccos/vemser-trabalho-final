package javamos_decolar;

public class Trecho {

    private String origem;
    private String destino;

    public Trecho(String origem, String destino) {
        this.origem = origem;
        this.destino = destino;
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

    public void imprimirTrecho() {
        System.out.printf(
                "\nOrigem: %s" +
                        "\nDestino: %s",
                getOrigem(), getDestino()
        );
    }
}
