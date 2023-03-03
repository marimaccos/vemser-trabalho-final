package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Comprador;
import br.com.dbc.javamosdecolar.model.Passagem;
import br.com.dbc.javamosdecolar.model.Status;
import br.com.dbc.javamosdecolar.model.Venda;
import br.com.dbc.javamosdecolar.model.dto.CreateVendaDTO;
import br.com.dbc.javamosdecolar.repository.VendaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VendaService {
    private final VendaRepository vendaRepository;
    private final PassagemService passagemService;
    private final CompradorService compradorService;
    private final CompanhiaService companhiaService;

    public Venda efetuarVenda(CreateVendaDTO vendaDTO) throws RegraDeNegocioException {

        try {
            UUID codigo = UUID.randomUUID();

            Passagem passagem = passagemService.getPassagemById(vendaDTO.getIdPassagem());

            Comprador comprador = compradorService.getCompradorById(vendaDTO.getIdComprador());

            Venda vendaEfetuada = vendaRepository.adicionar(new Venda(codigo.toString(), passagem, comprador,
                    passagem.getTrecho().getCompanhia(), LocalDateTime.now(), Status.CONCLUIDO));

            if(vendaEfetuada.equals(null)) {
                throw new RegraDeNegocioException("Não foi possível concluir a venda.");
            }

            boolean conseguiuEditar = passagemService.editarPassagemVendida(passagem);

            if(!conseguiuEditar) {
                throw new RegraDeNegocioException("Não foi possível concluir a venda.");
            }

            return vendaEfetuada;
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a compra.");
        }
    }

    public boolean cancelarVenda(Integer idVenda) throws RegraDeNegocioException {

        try {
            Venda venda = vendaRepository.getVendaPorId(idVenda)
                    .orElseThrow(() -> new RegraDeNegocioException("Venda não encontrada!"));

            if(venda.getStatus().getTipo() == 2) {
                throw new RegraDeNegocioException("Venda já cancelada!");
            }

            return vendaRepository.cancelarVenda(idVenda);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante o cancelamento.");
        }
    }

    public List<Venda> getHistoricoComprasComprador(Integer idComprador) throws RegraDeNegocioException {
        try {
            compradorService.getCompradorById(idComprador);
            return vendaRepository.getVendasPorComprador(idComprador);

         } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public Venda getVendaPorCodigo(String uuid) throws RegraDeNegocioException {
        try {
            vendaRepository.getVendaPorCodigo(uuid);
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a recuperação da venda.");
        }
    }

    public List<Venda> getHistoricoVendasCompanhia(Integer id) throws RegraDeNegocioException {
        try {
            companhiaService.getCompanhiaById(id);
            return vendaRepository.getVendasPorCompanhia(id);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }
}
