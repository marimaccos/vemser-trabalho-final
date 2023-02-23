package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.*;
import javamos_decolar.com.javamosdecolar.repository.*;
import javamos_decolar.com.javamosdecolar.utils.Codigo;

import java.time.LocalDate;

public class VendaService {

    private VendaRepository vendaRepository;
    private PassagemRepository passagemRepository;

    public VendaService() {
        vendaRepository = new VendaRepository();
        passagemRepository = new PassagemRepository();
    }

    public Venda efetuarVenda(Passagem passagem, Comprador comprador) throws DatabaseException {

        /*
            gera codigo de venda, mas consulta no banco pra ver se ja existe uma venda com esse codigo
            -> sugestão: ver se tem uma forma do oracle fazer isso automaticamente pra gente
         */

        boolean codigoJaExiste = true;
        String codigo = "";
        while(codigoJaExiste) {
            codigo = Codigo.gerarCodigo();
            if(vendaRepository.buscaVendaPorCodigo(codigo).isEmpty()) {
                codigoJaExiste = false;
            }
        }

        Venda vendaAtual = new Venda(codigo, passagem, comprador,
                passagem.getTrecho().getCompanhia(), LocalDate.now(), Status.CONCLUIDO);

        vendaRepository.adicionar(vendaAtual);
        passagemRepository.mudarStatusDaPassagem(2, passagem);

        return vendaAtual;
    }

}
