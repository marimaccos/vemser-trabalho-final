package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.model.Passagem;
import javamos_decolar.com.javamosdecolar.model.Venda;
import javamos_decolar.com.javamosdecolar.repository.CompradorRepository;
import javamos_decolar.com.javamosdecolar.repository.PassagemRepository;
import javamos_decolar.com.javamosdecolar.repository.VendaRepository;

import java.util.Optional;

public class CompradorService {

    private CompradorRepository compradorRepository;

    public CompradorService() {
        compradorRepository = new CompradorRepository();
    }

    public void imprimirHistorico() {
        //
    }

    public boolean comprarPassagem(String codigoPassagem, PassagemRepository passagemDados, VendaRepository vendaDados) {
//        Optional<Passagem> passagemOptional = passagemDados.pegarPassagemPorCodigo(codigoPassagem);
//        if(passagemOptional.isEmpty()) {
//            System.err.println("Passagem não pode ser encontrada");
//        } else {
//            Passagem passagem = passagemOptional.get();
//            Venda venda = new Venda();
//            venda.efetuarVenda(passagem, this, passagem.getTrecho().getCompanhia(), vendaDados);
//            System.out.println("Compra efetuada com sucesso!");
//        }
//
        return false;
    }
}
