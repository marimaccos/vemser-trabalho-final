package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.*;
import javamos_decolar.com.javamosdecolar.repository.*;
import javamos_decolar.com.javamosdecolar.utils.Codigo;

import java.time.LocalDate;
import java.util.Optional;

public class VendaService {

    private VendaRepository vendaRepository;
    private PassagemRepository passagemRepository;

    public VendaService() {
        vendaRepository = new VendaRepository();
        passagemRepository = new PassagemRepository();
    }

    public Venda efetuarVenda(Passagem passagem, Comprador comprador) {

        /*
            gera codigo de venda, mas consulta no banco pra ver se ja existe uma venda com esse codigo
            -> sugestão: ver se tem uma forma do oracle fazer isso automaticamente pra gente
         */

        try {
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
            passagemRepository.mudarDisponibilidadeDaPassagem(false, passagem);

            return vendaAtual;
        } catch (DatabaseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cancelarVenda(String codigo) {

        try {
            Optional<Venda> venda = vendaRepository.buscaVendaPorCodigo(codigo);

            if(venda.isEmpty()) {
                throw new Exception("Venda não pode ser encontrada!");
            }

            if(venda.get().getStatus().getTipo() == 3) {
                throw new Exception("Venda já cancelada!");
            }

            final int ID_VENDA = venda.get().getIdVenda();
            boolean vendaFoiCancelada = vendaRepository.cancelarVenda(ID_VENDA, venda.get());

            System.out.println("Venda foi cancelada? " + vendaFoiCancelada + " | id = " + ID_VENDA);
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }

    }
}
