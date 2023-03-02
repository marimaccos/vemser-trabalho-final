package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Passagem;
import br.com.dbc.javamosdecolar.model.Status;
import br.com.dbc.javamosdecolar.model.Venda;
import br.com.dbc.javamosdecolar.model.dto.CreateVendaDTO;
import br.com.dbc.javamosdecolar.repository.VendaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VendaService {
    private final VendaRepository vendaRepository;
    private final PassagemService passagemService;
    private final CompradorService compradorService;

    public Venda efetuarVenda(CreateVendaDTO vendaDTO) throws RegraDeNegocioException {

        try {
            UUID codigo = UUID.randomUUID();

            Optional<Passagem> passagemOptional = passagemService.getPassagemById(vendaDTO.getIdPassagem());

            if(passagem.isEmpty()) {
                throw new RegraDeNegocioException("Passagem inexistente.");
            }

            final Passagem PASSAGEM = passagemOptional.get();

            Optional<Comprador> comprador = compradorService.getCompradorById(vendaDTO.getIdComprador());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia inexistente.");
            }

            Venda vendaAtual = new Venda(codigo.toString(), PASSAGEM, comprador.get(),
                    PASSAGEM.getTrecho().getCompanhia(), LocalDateTime.now(), Status.CONCLUIDO);

            Venda vendaCriada = vendaRepository.adicionar(vendaAtual);
            boolean conseguiuEditar = passagemService.editarDisponibilidadeDaPassagem(false, vendaDTO.getIdPassagem());

            if(conseguiuEditar) {
                vendaAtual.getPassagem().setDisponivel(false);
            }

            passagemService.inserirIdVenda(idPassagem, vendaCriada);

            return vendaAtual;
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a compra.");
        }
    }

    public boolean cancelarVenda(Integer idVenda) throws RegraDeNegocioException {

        try {
            Optional<Venda> venda = vendaRepository.getVendaPorId(idVenda);

            if(venda.isEmpty()) {
                throw new RegraDeNegocioException("Venda não encontrada!");
            }

            if(venda.get().getStatus().getTipo() == 3) {
                throw new RegraDeNegocioException("Venda já cancelada!");
            }

            final int ID_VENDA = venda.get().getIdVenda();

            return vendaRepository.cancelarVenda(ID_VENDA);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante o cancelamento.");
        }
    }

    public List<Venda> getHistoricoVendasComprador(Integer idComprador) throws RegraDeNegocioException {
        try {
            Optional<Comprador> comprador = compradorService.getCompradorById(idComprador);

            if(comprador.isEmpty()) {
                throw new RegraDeNegocioException("Comprador inexistente");
            }

            return vendaRepository.getVendasPorComprador(idComprador);

         } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }
}
