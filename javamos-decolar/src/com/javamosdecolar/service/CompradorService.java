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

    public boolean comprarPassagem(String codigoPassagem, Usuario usuario) {
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

            vendaService.efetuarVenda(passagem.get(), comprador.get());
            return true;
        } catch (DatabaseException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            return false;
        }
    }
}
