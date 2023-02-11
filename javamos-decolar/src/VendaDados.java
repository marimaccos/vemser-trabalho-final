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

}
