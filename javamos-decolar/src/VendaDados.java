package javamos_decolar;

import java.util.ArrayList;
import java.util.List;

public class VendaDados {

    private List<Venda> listaDeVenda;

    public VendaDados() {
        this.listaDeVenda = new ArrayList<>();
    }

    public void adicionar(Venda comprador) {
        this.listaDeVenda.add(comprador);
    }

    public void listar() {
        for (int i = 0; i < listaDeVenda.size(); i++) {
            System.out.println("id=" + i + " | " + listaDeVenda.get(i));
        }
    }

    public void alterarStatus(Integer index, Status status) {
        Venda vendaProcurada = listaDeVenda.get(index);
        vendaProcurada.setStatus(status);
    }
}
