package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Comprador;
import br.com.dbc.javamosdecolar.model.Passagem;
import br.com.dbc.javamosdecolar.model.Status;
import br.com.dbc.javamosdecolar.model.Venda;
import br.com.dbc.javamosdecolar.model.dto.CreateVendaDTO;
import br.com.dbc.javamosdecolar.model.dto.VendaDTO;
import br.com.dbc.javamosdecolar.repository.VendaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendaService {
    private final VendaRepository vendaRepository;
    private final PassagemService passagemService;
    private final CompradorService compradorService;
    private final CompanhiaService companhiaService;
    private final EmailService emailService;
    private final ObjectMapper mapper;

    public VendaDTO efetuarVenda(CreateVendaDTO vendaDTO) throws RegraDeNegocioException {

        try {
            UUID codigo = UUID.randomUUID();

            Passagem passagem = mapper
                    .convertValue(passagemService.getPassagemById(vendaDTO.getIdPassagem()), Passagem.class);

            if(!passagem.isDisponivel()) {
                throw new RegraDeNegocioException("Passagem indisponível.");
            }

            Comprador comprador = mapper.convertValue(compradorService.getCompradorPorID(vendaDTO.getIdComprador()),
                    Comprador.class);

            Venda vendaEfetuada = vendaRepository.adicionar(new Venda(codigo.toString(), passagem, comprador,
                    passagem.getTrecho().getCompanhia(), LocalDateTime.now(), Status.CONCLUIDO));

            if(vendaEfetuada.equals(null)) {
                throw new RegraDeNegocioException("Não foi possível concluir a venda.");
            }

            boolean conseguiuEditar = passagemService.editarPassagemVendida(passagem);

            if(!conseguiuEditar) {
                throw new RegraDeNegocioException("Não foi possível concluir a venda.");
            }

            VendaDTO vendaEfetuadaDTO = mapper.convertValue(vendaEfetuada, VendaDTO.class);
            vendaEfetuadaDTO.setIdCompanhia(vendaEfetuada.getCompanhia().getIdCompanhia());
            vendaEfetuadaDTO.setIdPassagem(vendaEfetuada.getPassagem().getIdPassagem());
            vendaEfetuadaDTO.setIdComprador(vendaEfetuada.getComprador().getIdComprador());

            emailService.sendEmail(emailService.getVendaTemplate(vendaEfetuada, 1));

            return vendaEfetuadaDTO;
        } catch (DatabaseException e) {
            e.printStackTrace();
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

            emailService.sendEmail(emailService.getVendaTemplate(venda, 2));

            return vendaRepository.cancelarVenda(idVenda);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante o cancelamento.");
        }
    }

    public List<VendaDTO> getHistoricoComprasComprador(Integer idComprador) throws RegraDeNegocioException {
        try {
            compradorService.getCompradorPorID(idComprador);
            return vendaRepository.getVendasPorComprador(idComprador).stream()
                    .map(venda -> {
                        VendaDTO vendaDTO = mapper.convertValue(venda, VendaDTO.class);
                        vendaDTO.setIdComprador(venda.getComprador().getIdComprador());
                        vendaDTO.setIdPassagem(venda.getPassagem().getIdPassagem());
                        vendaDTO.setIdCompanhia(venda.getPassagem().getTrecho().getCompanhia().getIdCompanhia());

                        return vendaDTO;
                    }).toList();

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public VendaDTO getVendaPorCodigo(String uuid) throws RegraDeNegocioException {
        try {
            Venda venda = vendaRepository.getVendaPorCodigo(uuid)
                    .orElseThrow(() -> new RegraDeNegocioException("Venda não pode ser localizada."));

            VendaDTO vendaDTO = mapper.convertValue(venda, VendaDTO.class);
            vendaDTO.setIdComprador(venda.getComprador().getIdComprador());
            vendaDTO.setIdPassagem(venda.getPassagem().getIdPassagem());
            vendaDTO.setIdCompanhia(venda.getPassagem().getTrecho().getCompanhia().getIdCompanhia());

            return vendaDTO;
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a recuperação da venda.");
        }
    }

    public List<VendaDTO> getHistoricoVendasCompanhia(Integer id) throws RegraDeNegocioException {
        try {
            companhiaService.getCompanhiaById(id);
            return vendaRepository.getVendasPorCompanhia(id).stream()
                    .map(venda -> {
                        VendaDTO vendaDTO = mapper.convertValue(venda, VendaDTO.class);
                        vendaDTO.setIdComprador(venda.getComprador().getIdComprador());
                        vendaDTO.setIdPassagem(venda.getPassagem().getIdPassagem());
                        vendaDTO.setIdCompanhia(venda.getPassagem().getTrecho().getCompanhia().getIdCompanhia());

                        return vendaDTO;
                    }).toList();

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }
}
