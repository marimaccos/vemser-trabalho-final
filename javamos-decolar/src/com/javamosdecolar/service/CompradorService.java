package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.*;
import javamos_decolar.com.javamosdecolar.repository.CompradorRepository;
import javamos_decolar.com.javamosdecolar.repository.PassagemRepository;
import javamos_decolar.com.javamosdecolar.repository.VendaRepository;

import java.util.Optional;

public class CompradorService {

    private CompradorRepository compradorRepository;
    private PassagemRepository passagemRepository;
    private VendaService vendaService;

    public CompradorService() {
        compradorRepository = new CompradorRepository();
    }

    public void imprimirHistorico() {
        //historico de compras do comprador
    }

    public void comprarPassagem(String codigoPassagem, Usuario usuario) {
        try {
            Optional<Comprador> comprador = compradorRepository
                    .acharCompradorPorIdUsuario(usuario.getIdUsuario());

            if(comprador.isEmpty()) {
                throw new Exception("Comprador não existe!");
            }

            Optional<Passagem> passagem = passagemRepository.pegarPassagemPorCodigo(codigoPassagem);

            if(passagem.isEmpty()) {
                throw new Exception("Passagem não existe!");
            }

            if(!passagem.get().isDisponivel()) {
                throw new Exception("Passagem indisponível!");
            }

            Venda venda = vendaService.efetuarVenda(passagem.get(), comprador.get());

            System.out.println("Venda criada com sucesso! " + venda);
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }
}
