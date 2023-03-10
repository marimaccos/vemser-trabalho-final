package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.*;
import br.com.dbc.javamosdecolar.dto.VendaCreateDTO;
import br.com.dbc.javamosdecolar.dto.VendaDTO;
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

    public VendaDTO create(VendaCreateDTO vendaDTO) throws RegraDeNegocioException {

        try {
            UUID codigo = UUID.randomUUID();

            Passagem passagem = mapper
                    .convertValue(passagemService.getById(vendaDTO.getIdPassagem()), Passagem.class);

            if(!passagem.isDisponivel()) {
                throw new RegraDeNegocioException("Passagem indisponível.");
            }

            Comprador comprador = mapper.convertValue(compradorService.getById(vendaDTO.getIdComprador()),
                    Comprador.class);

            if(!comprador.isAtivo()) {
                throw new RegraDeNegocioException("Comprador indisponível.");
            }

            Companhia companhia = mapper.convertValue(companhiaService.getById(vendaDTO.getIdCompanhia()),
                    Companhia.class);

            if(!companhia.isAtivo()) {
                throw new RegraDeNegocioException("Companhia indisponível.");
            }

            Venda vendaEfetuada = vendaRepository.create(new Venda(codigo.toString(), passagem, comprador,
                    companhia, LocalDateTime.now(), Status.CONCLUIDO));

            if(vendaEfetuada.equals(null)) {
                throw new RegraDeNegocioException("Não foi possível concluir a venda.");
            }

            boolean conseguiuEditar = passagemService.updatePassagemVendida(passagem, vendaEfetuada.getIdVenda());

            if(!conseguiuEditar) {
                throw new RegraDeNegocioException("Não foi possível concluir a venda.");
            }

            VendaDTO vendaEfetuadaDTO = mapper.convertValue(vendaEfetuada, VendaDTO.class);
            vendaEfetuadaDTO.setIdCompanhia(vendaEfetuada.getCompanhia().getIdCompanhia());
            vendaEfetuadaDTO.setIdPassagem(vendaEfetuada.getPassagem().getIdPassagem());
            vendaEfetuadaDTO.setIdComprador(vendaEfetuada.getComprador().getIdComprador());

            emailService.sendEmail(vendaEfetuada, "CRIAR", comprador);

            return vendaEfetuadaDTO;
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a compra.");
        }
    }

    public boolean delete(Integer idVenda) throws RegraDeNegocioException {

        try {
            Venda venda = vendaRepository.getById(idVenda)
                    .orElseThrow(() -> new RegraDeNegocioException("Venda não encontrada!"));

            //tirar isso quando implementar o springdata
            Comprador comprador = mapper
                    .convertValue(compradorService.getById(venda.getComprador().getIdComprador()),
                    Comprador.class);

            if(venda.getStatus().getTipo() == 2) {
                throw new RegraDeNegocioException("Venda já cancelada!");
            }

            emailService.sendEmail(venda, "DELETAR", comprador);

            return vendaRepository.delete(idVenda);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante o cancelamento.");
        }
    }

    public VendaDTO getByCodigo(String uuid) throws RegraDeNegocioException {
        try {
            Venda venda = vendaRepository.getByCodigo(uuid)
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

    public List<VendaDTO> getHistoricoComprasComprador(Integer idComprador) throws RegraDeNegocioException {
        try {
            compradorService.getById(idComprador);
            return vendaRepository.getByComprador(idComprador).stream()
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

    public List<VendaDTO> getHistoricoVendasCompanhia(Integer id) throws RegraDeNegocioException {
        try {
            companhiaService.getById(id);
            return vendaRepository.getByCompanhia(id).stream()
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
