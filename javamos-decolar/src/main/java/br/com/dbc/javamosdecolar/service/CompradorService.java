package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Comprador;
import br.com.dbc.javamosdecolar.model.Passagem;
import br.com.dbc.javamosdecolar.model.Venda;
import br.com.dbc.javamosdecolar.model.dto.CompradorCreateDTO;
import br.com.dbc.javamosdecolar.model.dto.CompradorDTO;
import br.com.dbc.javamosdecolar.repository.CompradorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompradorService {

    private final CompradorRepository compradorRepository;
    private final PassagemService passagemService;
    private final VendaService vendaService;
    private final ObjectMapper objectMapper;

    public CompradorDTO cadastrar(CompradorCreateDTO comprador) {
        try {
            Comprador compradorEntity = objectMapper.convertValue(comprador, Comprador.class);


            return objectMapper.convertValue(/*comprador*/, CompradorDTO.class);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante o cadastro.");
        }
    }

    public List<CompradorDTO> listaCompradores() throws RegraDeNegocioException{
        try {
            List<CompradorDTO> listaCompradores = compradorRepository.listaCompradores().stream()
                    .map(comprador -> objectMapper.convertValue(comprador, CompradorDTO.class))
                    .toList();

            return listaCompradores;

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public CompradorDTO getCompradorPorID(Integer idComprador) throws RegraDeNegocioException{
        try {
            Comprador compradorEncontrado = compradorRepository.getCompradorPorID(idComprador)
                    .orElseThrow(() -> new RegraDeNegocioException("Comprador não encontrado!"));

            return objectMapper.convertValue(compradorEncontrado, CompradorDTO.class);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    // Ainda permanece?
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
