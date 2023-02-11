package javamos_decolar;

import java.util.ArrayList;
import java.util.List;

public class CompradorDados implements Crud<Comprador> {

    private List<Comprador> listaDeComprador;

    public CompradorDados() {
        this.listaDeComprador = new ArrayList<>();
    }

    @Override
    public void adicionar(Comprador comprador) {
        this.listaDeComprador.add(comprador);
    }

    @Override
    public void listar() {
        for (int i = 0; i < listaDeComprador.size(); i++) {
            System.out.println("id=" + i + " | " + listaDeComprador.get(i));
        }
    }

    @Override
    public void editar(Integer index, Comprador comprador) {
        Comprador compradorProcurado = listaDeComprador.get(index);
        compradorProcurado.setNome(comprador.getNome());
        compradorProcurado.setSenha(comprador.getSenha());
    }

    @Override
    public void remover(Integer index) {
        this.listaDeComprador.remove(index.intValue());
    }
}
