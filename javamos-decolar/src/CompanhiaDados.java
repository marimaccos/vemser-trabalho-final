package javamos_decolar;

import java.util.ArrayList;
import java.util.List;

public class CompanhiaDados implements Crud<Companhia> {

    List<Companhia> listaDeCompanhias = new ArrayList<>();

    @Override
    public void adicionar(Companhia companhia) {
        listaDeCompanhias.add(companhia);
    }

    @Override
    public void listar() {
        for (int i = 0; i < listaDeCompanhias.size(); i++) {
            System.out.println("id: " + i + " | " + listaDeCompanhias.get(i));
        }
    }

    @Override
    public void editar(Integer index, Companhia companhia) {

        listaDeCompanhias.get(index).setNome(companhia.getNome());
        listaDeCompanhias.get(index).setSenha(companhia.getSenha());

    }

    @Override
    public void remover(Integer index) {

        listaDeCompanhias.remove(index.intValue());
    }
}
