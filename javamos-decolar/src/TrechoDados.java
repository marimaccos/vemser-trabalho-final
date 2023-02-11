package javamos_decolar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrechoDados implements Crud<Trecho>{

    List<Trecho> listaDeTrechos = new ArrayList<Trecho>();

    public List<Trecho> getListaDeTrechos() {
        return listaDeTrechos;
    }

    @Override
    public void adicionar(Trecho trecho) {
        listaDeTrechos.add(trecho);
    }

    @Override
    public void listar() {
        for (int i = 0; i < listaDeTrechos.size(); i++) {
            System.out.println("id: " + i + " | " + listaDeTrechos.get(i));
        }
    }

    @Override
    public void editar(Integer index, Trecho trecho) {
        if (trecho.getOrigem().isBlank() || trecho.getDestino().isBlank()) {
            System.err.println("Campo não pode ser vazio!");
        } else {
            Trecho trechoEncontrado = listaDeTrechos.get(index);
            trechoEncontrado.setOrigem(trecho.getOrigem());
            trechoEncontrado.setDestino(trecho.getDestino());
        }
    }

    @Override
    public void remover(Integer index) {
        listaDeTrechos.remove(index.intValue());
    }

    public boolean checaSeOTrechoExiste(String destino, String origem, Companhia companhia) {
        return this.getListaDeTrechos().stream()
                .anyMatch(trecho -> trecho.getDestino().equals(destino)
                        && trecho.getOrigem().equals(origem)
                        && trecho.getCompanhia().equals(companhia));
    }

    public Optional<Trecho> buscarTrecho(String destino, String origem, Companhia companhia) {
        return this.getListaDeTrechos().stream().filter(trecho ->
                trecho.getDestino().equals(destino)
                && trecho.getOrigem().equals(origem)
                && trecho.getCompanhia().equals(companhia)).findFirst();
    }

}
