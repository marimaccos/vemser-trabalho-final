package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.exceptions.RegraDeNegocioException;
import javamos_decolar.com.javamosdecolar.model.*;
import javamos_decolar.com.javamosdecolar.repository.CompradorRepository;
import javamos_decolar.com.javamosdecolar.repository.PassagemRepository;

import java.util.Optional;

public class CompradorService {

    private CompradorRepository compradorRepository;
    private PassagemRepository passagemRepository;
    private VendaService vendaService;

    public CompradorService() {
        compradorRepository = new CompradorRepository();
        passagemRepository = new PassagemRepository();
        vendaService = new VendaService();
    }

    public void comprarPassagem(String codigoPassagem, Usuario usuario) throws RegraDeNegocioException {
        try {
            Optional<Comprador> comprador = compradorRepository
                    .acharCompradorPorIdUsuario(usuario.getIdUsuario());

            if(comprador.isEmpty()) {
                throw new RegraDeNegocioException("Comprador não existe!");
            }

            Optional<Passagem> passagem = passagemRepository.getPassagemPorCodigo(codigoPassagem);

            if(passagem.isEmpty()) {
                throw new RegraDeNegocioException("Passagem não existe!");
            }

            if(!passagem.get().isDisponivel()) {
                throw new RegraDeNegocioException("Passagem indisponível!");
            }

            Venda venda = vendaService.efetuarVenda(passagem.get(), comprador.get());

            System.out.println("Venda criada com sucesso! " + venda);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a compra.");
        }
    }
}
