package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.model.Status;
import br.com.dbc.javamosdecolar.model.Venda;
import br.com.dbc.javamosdecolar.model.dto.CreateVendaDTO;
import br.com.dbc.javamosdecolar.repository.VendaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VendaService {
    private final VendaRepository vendaRepository;
    private final PassagemService passagemService;
    private final CompradorService compradorService;

    public VendaService(VendaRepository vendaRepository,
                        PassagemService passagemService,
                        CompradorService compradorService) {
        this.vendaRepository = vendaRepository;
        this.passagemService = passagemService;
        this.compradorService = compradorService;
    }

    public Venda efetuarVenda(CreateVendaDTO vendaDTO) throws RegraDeNegocioException, Exception {

        try {
            UUID codigo = UUID.randomUUID();

            Optional<Passagem> passagem = passagemService.getPassagemById(vendaDTO.getIdPassagem());

            if(passagem.isEmpty()) {
                throw new Exception("Passagem inexistente.");
            }

            Optional<Companhia> companhia = compradorService.getCompanhiaById(vendaDTO.getIdPassagem());

            if(companhia.isEmpty()) {
                throw new Exception("Passagem inexistente.");
            }

            Venda vendaAtual = new Venda(codigo.toString(), passagem, comprador.get().getIdComprador(),
                    passagem.get().getTrecho().getCompanhia(), LocalDateTime.now(), Status.CONCLUIDO);

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

    public void cancelarVenda(Integer idVenda) throws RegraDeNegocioException {

        try {
            Optional<Venda> venda = vendaRepository.getVendaPorCodigo(codigo);

            if(venda.isEmpty()) {
                throw new RegraDeNegocioException("Venda não encontrada!");
            }

            if(venda.get().getStatus().getTipo() == 3) {
                throw new RegraDeNegocioException("Venda já cancelada!");
            }

            final int ID_VENDA = venda.get().getIdVenda();
            boolean vendaFoiCancelada = vendaRepository.cancelarVenda(ID_VENDA);

            System.out.println("Venda foi cancelada? " + vendaFoiCancelada + " | id = " + ID_VENDA);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante o cancelamento.");
        }
    }

    public void getHistoricoVendasComprador(Integer idUsuario) throws RegraDeNegocioException {
        try {
            Optional<Comprador> comprador = compradorService.acharCompradorPorIdUsuario(idUsuario);

            if(comprador.isEmpty()) {
                throw new RegraDeNegocioException("Comprador inexistente");
            }

            List<Venda> vendasPorComprador = vendaRepository.getVendasPorComprador(comprador.get().getIdComprador());

            if (vendasPorComprador.isEmpty()) {
                System.out.println("Não há Histórico!");
            } else {
                vendasPorComprador.stream().forEach(System.out::println);
            }

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }
}
