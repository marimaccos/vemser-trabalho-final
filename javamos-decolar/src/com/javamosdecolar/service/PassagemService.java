package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.*;
import javamos_decolar.com.javamosdecolar.repository.*;
import javamos_decolar.com.javamosdecolar.utils.Codigo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PassagemService {

    private PassagemRepository passagemRepository;
    private TrechoRepository trechoRepository;
    private CompanhiaRepository companhiaRepository;
    private CompradorRepository compradorRepository;

    public PassagemService() {
        passagemRepository = new PassagemRepository();
        trechoRepository = new TrechoRepository();
        companhiaRepository = new CompanhiaRepository();
        compradorRepository = new CompradorRepository();
    }

    public void cadastrarPassagem(Passagem novaPassagem, String trecho, Usuario usuario) {

        try {

            boolean codigoJaExiste = true;
            String codigo = "";
            while(codigoJaExiste) {
                codigo = Codigo.gerarCodigo();
                if(passagemRepository.pegarPassagemPorCodigo(codigo).isEmpty()) {
                    codigoJaExiste = false;
                }
            }

            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new Exception("Companhia inválida!");
            }

            String[] origemEDestino = trecho.split("/");

            Optional<Trecho> trechoEncontrado = trechoRepository
                    .buscarTrecho(origemEDestino[0], origemEDestino[1], companhia.get());

            if(trechoEncontrado.isEmpty()) {
                throw new Exception("Trecho inválido!");
            }

            novaPassagem.setTrecho(trechoEncontrado.get());
            novaPassagem.setCodigo(codigo);

            Passagem passagemCriada = passagemRepository.adicionar(novaPassagem);
            System.out.println("Passagem criada com sucesso! " + passagemCriada);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }

    public void listarPassagemPorData(LocalDate data, int tipoDeData) {
        try {
            if(tipoDeData == 1) {
                passagemRepository.pegarPassagemPorDataPartida(data).stream()
                        .forEach(System.out::println);
            } else if (tipoDeData == 2) {
                passagemRepository.pegarPassagemPorDataChegada(data).stream()
                        .forEach(System.out::println);
            } else {
                throw new Exception("Opção inválida!");
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }

    public void listarPassagemPorValorMaximo(BigDecimal valorMaximo) {
        try {
            passagemRepository.pegarPassagemPorValor(valorMaximo).stream().forEach(System.out::println);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void listarPassagemPorCompanhia(String nomeCompanhia) {
        try {
            Optional<Companhia> companhiaEncontrada = companhiaRepository.buscaCompanhiaPorNome(nomeCompanhia);

            if(companhiaEncontrada.isEmpty()) {
                throw new Exception("Companhia não encontrada!");
            }

            List<Passagem> passagems = passagemRepository
                    .pegarPassagemPorCompanhia(companhiaEncontrada.get().getIdCompanhia());

            passagems.stream().forEach(System.out::println);

        } catch (DatabaseException e) {
            e.printStackTrace();
        }  catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }

    public void listarUltimasPassagens() {
        try {
            passagemRepository.pegarUltimasPassagens().stream().forEach(System.out::println);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void listarHistoricoDePassagensComprador(Usuario usuario) {
        try {
            Optional<Comprador> comprador = compradorRepository.acharCompradorPorIdUsuario(usuario.getIdUsuario());

            if(comprador.isEmpty()) {
                throw new Exception("Comprador inexistente");
            }

            passagemRepository.pegarPassagensPorComprador(comprador.get().getIdComprador())
                    .stream().forEach(System.out::println);

        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }
}
