package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.exceptions.RegraDeNegocioException;
import javamos_decolar.com.javamosdecolar.model.*;
import javamos_decolar.com.javamosdecolar.repository.*;
import javamos_decolar.com.javamosdecolar.utils.Codigo;

import java.time.LocalDateTime;
import java.util.Optional;

public class VendaService {

    private VendaRepository vendaRepository;
    private PassagemRepository passagemRepository;
    private CompradorRepository compradorRepository;

    public VendaService() {
        vendaRepository = new VendaRepository();
        passagemRepository = new PassagemRepository();
        compradorRepository = new CompradorRepository();
    }

    public Venda efetuarVenda(Passagem passagem, Comprador comprador) throws RegraDeNegocioException {

        /*
            gera codigo de venda, mas consulta no banco pra ver se ja existe uma venda com esse codigo
            -> sugestão: ver se tem uma forma do oracle fazer isso automaticamente pra gente
         */

        try {
            boolean codigoJaExiste = true;
            String codigo = "";
            while(codigoJaExiste) {
                codigo = Codigo.gerarCodigo();
                if(vendaRepository.getVendaPorCodigo(codigo).isEmpty()) {
                    codigoJaExiste = false;
                }
            }

            Venda vendaAtual = new Venda(codigo, passagem, comprador,
                    passagem.getTrecho().getCompanhia(), LocalDateTime.now(), Status.CONCLUIDO);

            vendaRepository.adicionar(vendaAtual);
            passagemRepository.editarDisponibilidadeDaPassagem(false, passagem);

            return vendaAtual;
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a compra.");
        }
    }

    public void cancelarVenda(String codigo) throws RegraDeNegocioException {

        try {
            Optional<Venda> venda = vendaRepository.getVendaPorCodigo(codigo);

            if(venda.isEmpty()) {
                throw new RegraDeNegocioException("Venda não pode ser encontrada!");
            }

            if(venda.get().getStatus().getTipo() == 3) {
                throw new RegraDeNegocioException("Venda já cancelada!");
            }

            final int ID_VENDA = venda.get().getIdVenda();
            boolean vendaFoiCancelada = vendaRepository.cancelarVenda(ID_VENDA);

            System.out.println("Venda foi cancelada? " + vendaFoiCancelada + " | id = " + ID_VENDA);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante o cancelamento.");
        }
    }

    public void getHistoricoVendasComprador(Usuario usuario) throws RegraDeNegocioException {
        try {
            Integer idUsuario = usuario.getIdUsuario();
            Optional<Comprador> comprador = compradorRepository.acharCompradorPorIdUsuario(idUsuario);

            if(comprador.isEmpty()) {
                throw new RegraDeNegocioException("Comprador inexistente");
            }

            vendaRepository.getVendasPorComprador(comprador.get().getIdComprador())
                    .stream().forEach(System.out::println);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }
}
