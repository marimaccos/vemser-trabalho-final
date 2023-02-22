package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.model.Trecho;
import javamos_decolar.com.javamosdecolar.repository.TrechoRepository;

public class TrechoService {

    private TrechoService trechoService;

    public TrechoService() {
        trechoService = new TrechoService();
    }

    public boolean criarTrecho(Trecho trechoDesejado, TrechoRepository trechoDados) {

        boolean oTrechoExiste = trechoDados.checaSeOTrechoExiste(trechoDesejado.getDestino(),
                trechoDesejado.getOrigem(), this);

        if(!oTrechoExiste) {
            trechoDados.adicionar(trechoDesejado);
            this.getTrechosCadastrados().add(trechoDesejado);
            return true;
        }

        System.err.println("Trecho já cadastrado!");
        return false;
    }

    public boolean editarTrecho(Integer index, Trecho trechoDesejado, TrechoRepository trechoDados) {

        boolean oTrechoExiste = trechoDados.checaSeOTrechoExiste(trechoDesejado.getDestino(),
                trechoDesejado.getOrigem(), this);

        if(!oTrechoExiste) {
            trechoDados.editar(index, trechoDesejado);
            return true;
        }

        System.err.println("Trecho já cadastrado!");
        return false;
    }

    public boolean deletarTrecho(Integer index, TrechoRepository trechoDados) {

        if(index > this.getTrechosCadastrados().size()) {
            System.err.println("Index não existente.");
            return false;
        }

        Trecho trecho = this.getTrechosCadastrados().get(index);
        System.out.println(trecho);
        Integer indexNoTrechoDados = trechoDados.getListaDeTrechos().indexOf(trecho);

        if(indexNoTrechoDados != null) {
            // remove da lista de trechos cadastrados da companhia
            this.getTrechosCadastrados().remove(index.intValue());
            // remove do "banco de dados" de trechos
            trechoDados.remover(indexNoTrechoDados);
            return true;
        }

        System.err.println("Trecho não cadastrado!");
        return false;
    }


}
