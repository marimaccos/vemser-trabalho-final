package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.exceptions.RegraDeNegocioException;
import javamos_decolar.com.javamosdecolar.model.*;
import javamos_decolar.com.javamosdecolar.repository.*;
import javamos_decolar.com.javamosdecolar.utils.Codigo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PassagemService {

    private PassagemRepository passagemRepository;
    private TrechoRepository trechoRepository;
    private CompanhiaRepository companhiaRepository;

    public PassagemService() {
        passagemRepository = new PassagemRepository();
        trechoRepository = new TrechoRepository();
        companhiaRepository = new CompanhiaRepository();
    }

    public void cadastrarPassagem(Passagem novaPassagem, String trecho, Usuario usuario) throws RegraDeNegocioException {
        /*
            gera codigo de passagem, mas consulta no banco pra ver se ja existe uma venda com esse codigo
            -> sugestão: ver se tem uma forma do oracle fazer isso automaticamente pra gente
         */

        try {
            final boolean DIA_ANTERIOR = novaPassagem.getDataChegada().isBefore(novaPassagem.getDataPartida());

            if(DIA_ANTERIOR) {
                throw new RegraDeNegocioException("Data inválida!");
            }

            boolean codigoJaExiste = true;
            String codigo = "";

            while(codigoJaExiste) {
                codigo = Codigo.gerarCodigo();
                if(passagemRepository.getPassagemPorCodigo(codigo).isEmpty()) {
                    codigoJaExiste = false;
                }
            }

            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia inválida!");
            }

            String[] origemEDestino = trecho.split("/");

            Optional<Trecho> trechoEncontrado = trechoRepository
                    .getTrecho(origemEDestino[0], origemEDestino[1], companhia.get());

            if(trechoEncontrado.isEmpty()) {
                throw new RegraDeNegocioException("Trecho inválido!");
            }

            novaPassagem.setTrecho(trechoEncontrado.get());
            novaPassagem.setCodigo(codigo);

            Passagem passagemCriada = passagemRepository.adicionar(novaPassagem);
            System.out.println("Passagem criada com sucesso! " + passagemCriada);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante o cadastro.");
        }
    }

    public void listarPassagemPorData(LocalDateTime data, int tipoDeData) throws RegraDeNegocioException {
        try {
            if(tipoDeData == 1) {
                List<Passagem> passagemPorDataPartida = passagemRepository.getPassagemPorDataPartida(data);
                if (passagemPorDataPartida.size() == 0) {
                    System.out.println("Não há passagens com essa data.");
                } else {
                    passagemPorDataPartida.stream().forEach(System.out::println);
                }

            } else if (tipoDeData == 2) {
                List<Passagem> passagemPorDataChegada = passagemRepository.getPassagemPorDataChegada(data);

                if (passagemPorDataChegada.size() == 0) {
                    System.out.println("Não há passagens com essa data.");
                } else {
                    passagemPorDataChegada.stream().forEach(System.out::println);
                }
            } else {
                throw new RegraDeNegocioException("Opção inválida!");
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public void listarPassagemPorValorMaximo(BigDecimal valorMaximo) throws RegraDeNegocioException {
        try {
            passagemRepository.getPassagemPorValor(valorMaximo).stream().forEach(System.out::println);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public void listarPassagemPorCompanhia(String nomeCompanhia) throws RegraDeNegocioException {
        try {
            Optional<Companhia> companhiaEncontrada = companhiaRepository.buscaCompanhiaPorNome(nomeCompanhia);

            if(companhiaEncontrada.isEmpty()) {
                throw new RegraDeNegocioException("Companhia não encontrada!");
            }

            List<Passagem> passagems = passagemRepository
                    .getPassagemPorCompanhia(companhiaEncontrada.get().getIdCompanhia());

            passagems.stream().forEach(System.out::println);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public void listarUltimasPassagens() throws RegraDeNegocioException {
        try {

            List<Passagem> ultimasPassagens = passagemRepository.getUltimasPassagens();

            if (ultimasPassagens.isEmpty()) {
                System.out.println("Não há passagens disponíveis!");
            } else {
                ultimasPassagens.stream().forEach(System.out::println);
            }

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public void editarPassagem(Passagem passagemEditada, String trecho, Usuario usuario) throws RegraDeNegocioException {
        try {
            final boolean DIA_ANTERIOR = passagemEditada.getDataChegada().isBefore(passagemEditada.getDataPartida());

            if(DIA_ANTERIOR) {
                throw new RegraDeNegocioException("Data inválida!");
            }

            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia inválida!");
            }

            Optional<Passagem> passagem = passagemRepository.getPassagemPorCodigo(passagemEditada.getCodigo());

            if(passagem.isEmpty()) {
                throw new RegraDeNegocioException("Passagem inválida!");
            }

            String[] origemEDestino = trecho.split("/");

            Optional<Trecho> trechoEncontrado = trechoRepository
                    .getTrecho(origemEDestino[0], origemEDestino[1], companhia.get());

            if(trechoEncontrado.isEmpty()) {
                throw new RegraDeNegocioException("Trecho inválido!");
            }

            passagemEditada.setTrecho(trechoEncontrado.get());

            final Integer ID_PASSAGEM = passagem.get().getIdPassagem();

            boolean conseguiuEditar = passagemRepository.editar(ID_PASSAGEM, passagemEditada);
            System.out.println("editado? " + conseguiuEditar + "| com id=" + ID_PASSAGEM);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }
}
