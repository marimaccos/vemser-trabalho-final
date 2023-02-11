package javamos_decolar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanhiaDados implements Crud<Companhia> {

    List<Companhia> listaDeCompanhias = new ArrayList<>();


    public List<Companhia> getListaDeCompanhias() {
        return listaDeCompanhias;
    }

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
    public Optional<Companhia> buscaCompanhiaPorLogin(String login) {
        Optional<Companhia> companhiaEncontrada = getListaDeCompanhias().stream()
                .filter(companhia -> companhia.getLogin().equals(login))
                .findFirst();

        return companhiaEncontrada;
    }

    public Optional<Companhia> buscaCompanhiaPorNome(String nome) {
        Optional<Companhia> companhiaEncontrada = getListaDeCompanhias().stream()
                .filter(companhia -> companhia.getNome().equals(nome))
                .findFirst();

        return companhiaEncontrada;
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
