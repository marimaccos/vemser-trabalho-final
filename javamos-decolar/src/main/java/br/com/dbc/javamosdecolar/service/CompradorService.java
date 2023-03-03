package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Comprador;
import br.com.dbc.javamosdecolar.model.Passagem;
import br.com.dbc.javamosdecolar.model.Venda;
import br.com.dbc.javamosdecolar.repository.CompradorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CompradorService {

    private final CompradorRepository compradorRepository;
    private final PassagemService passagemService;
    private final VendaService vendaService;

    public void comprarPassagem(String codigoPassagem, Usuario usuario) throws RegraDeNegocioException {
        try {
            Optional<Comprador> comprador = compradorRepository
                    .acharCompradorPorIdUsuario(usuario.getIdUsuario());

            if(comprador.isEmpty()) {
                throw new RegraDeNegocioException("Comprador não existe!");
            }

            Optional<Passagem> passagem = passagemService.getPassagemPorCodigo(codigoPassagem);

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
