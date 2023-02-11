package javamos_decolar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PassagemDados implements Crud<Passagem> {

    List<Passagem> listaDePassagens = new ArrayList<Passagem>();

    public List<Passagem> getListaDePassagens() {
        return listaDePassagens;
    }

    @Override
    public void adicionar(Passagem passagem) {
        listaDePassagens.add(passagem);
    }

    @Override
    public void listar() {
        for (int i = 0; i < listaDePassagens.size(); i++) {
            System.out.println("id: " + i + " | " + listaDePassagens.get(i));
        }
    }

    @Override
    public void editar(Integer index, Passagem passagem) {
        Passagem passagemEditada = listaDePassagens.get(index);
        passagemEditada.setDataPartida(passagem.getDataPartida());
        passagemEditada.setDataChegada(passagem.getDataChegada());
        passagemEditada.setTrecho(passagem.getTrecho());
        passagemEditada.setDisponivel(passagem.isDisponivel());
        passagemEditada.setValor(passagem.getValor());
    }

    @Override
    public void remover(Integer index) {
        listaDePassagens.remove(index.intValue());
    }

    public List<Passagem> pegarPassagemPorCompanhia (Companhia companhia) {
        return this.getListaDePassagens().stream()
                .filter(p -> p.getTrecho().getCompanhia().equals(companhia))
                .collect(Collectors.toList());
    }
}
