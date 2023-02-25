package javamos_decolar.com.javamosdecolar.view;

import javamos_decolar.com.javamosdecolar.exceptions.RegraDeNegocioException;
import javamos_decolar.com.javamosdecolar.model.*;
import javamos_decolar.com.javamosdecolar.repository.*;
import javamos_decolar.com.javamosdecolar.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        UsuarioService usuarioService = new UsuarioService();
        CompanhiaService companhiaService = new CompanhiaService();
        CompradorService compradorService = new CompradorService();
        PassagemService passagemService = new PassagemService();
        VendaService vendaService = new VendaService();
        TrechoService trechoService = new TrechoService();

        Usuario usuarioLogado = null;

        Scanner scanner = new Scanner(System.in);
        Integer opcao = 3;

        final DateTimeFormatter FORMATACAO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // MENU
        System.out.println("-------------------------------");
        System.out.println("BEM-VINDO AO SISTEMA JAVAMOS DECOLAR!");
        while (opcao != 0) {
            System.out.println("-------------------------------");
            System.out.println("\t\tMENU PRINCIPAL");
            System.out.println("-------------------------------");
            System.out.println("[1] - Cadastrar Usuário\n" +
                               "[2] - Entrar com Usuário Existente\n" +
                               "[0] - Sair");

            opcao = scanner.nextInt();
            scanner.nextLine();

            try {


                switch (opcao) {
                    case 1:
                        cadastrarUsuario(scanner, usuarioService);
                        break;

                    case 2:
                        usuarioLogado = entrarComUsuarioExistente(scanner, usuarioService);

                        if (usuarioLogado.getTipoUsuario().getTipo() == 1) { // talvez essa comparação esteja errada
                            exibeMenuDeUsuarioCompanhia(scanner, FORMATACAO_DATA, companhiaService, usuarioLogado,
                                    passagemService, trechoService);

                        } else if (usuarioLogado.getTipoUsuario().getTipo() == 2) {
                            exibeMenuDeUsuarioComprador(scanner, passagemService, usuarioLogado, vendaService,
                                    compradorService, FORMATACAO_DATA);
                            break;
                        } else {
                            break;
                        }
                    case 0:
                        opcao = 0;
                        break;
                    default:
                        System.err.println("Opção inválida.");
                        break;
                }

            } catch (RegraDeNegocioException er) {
                System.out.println(er.getMessage());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void cadastrarUsuario(Scanner scanner, UsuarioService usuarioService)
                                            throws RegraDeNegocioException {
        System.out.println("-------------------------------");
        System.out.println("CADASTRAR USUÁRIO");
        System.out.println("-------------------------------");
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine().toLowerCase();
        System.out.print("Digite seu login: ");
        String login = scanner.nextLine().toLowerCase();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();
        System.out.println("Digite o tipo de usuário: ");
        System.out.println("[1] - Companhia\n" +
                           "[2] - Comprador");
        String tipo = scanner.nextLine();

        if (tipo.equals("1")) {
            System.out.print("Digite cnpj: ");
            String cnpj = scanner.nextLine();
            System.out.print("Digite o nome fantasia: ");
            String nomeFantasia = scanner.nextLine().toLowerCase();

            Usuario usuario = new Usuario(login, senha, nome, TipoUsuario.COMPANHIA);
            usuarioService.criarUsuarioCompanhia(usuario, cnpj, nomeFantasia);

        } else if (tipo.equals("2")) {
            System.out.print("Digite cpf: ");
            String cpf = scanner.nextLine();

            Usuario usuario = new Usuario(login, senha, nome, TipoUsuario.COMPRADOR);
            usuarioService.criarUsuarioComprador(usuario, cpf);

        } else {
            System.err.println("Tipo Usuário inválido!");
        }
    }

    private static Usuario entrarComUsuarioExistente(Scanner scanner, UsuarioService usuarioService)
                                                        throws RegraDeNegocioException {
        System.out.println("-------------------------------");
        System.out.println("\t\tLOGIN");
        System.out.println("-------------------------------");
        System.out.print("Digite seu login: ");
        String login = scanner.nextLine().toLowerCase();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        Optional<Usuario> usuario = usuarioService.entrarComUsuarioExistente(login, senha);

        if(usuario.isPresent()) {
            return usuario.get();
        } else {
            return null;
        }
    }

    private static void exibeMenuDeUsuarioCompanhia(Scanner scanner, DateTimeFormatter formatacaoData,
                                                        CompanhiaService companhiaService, Usuario usuario,
                                                        PassagemService passagemService, TrechoService trechoService)
                                                        throws RegraDeNegocioException {
        String opcao = "";
        while (!opcao.equals("0")) {
            System.out.println("-------------------------------");
            System.out.println("BEM VINDO AO MENU DA COMPANHIA");
            System.out.println("-------------------------------");
            System.out.println("Escolha uma das opções abaixo:");
            System.out.println("[1] - Cadastrar Passagem");
            System.out.println("[2] - Editar Passagem");
            System.out.println("[3] - Remover Passagem");
            System.out.println("[4] - Passagens Cadastradas");
            System.out.println("[5] - Cadastrar Trecho");
            System.out.println("[6] - Editar Trecho");
            System.out.println("[7] - Remover Trecho");
            System.out.println("[8] - Trechos Cadastrados");
            System.out.println("[9] - Historico de Vendas");
            System.out.println("[0] - Sair");

            opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA - CADASTRAR PASSAGEM");
                    System.out.println("-------------------------------");
                    System.out.println("Insira a data de partida: Ex: dd/MM/yyyy HH:mm ");
                    LocalDateTime dataPartida = LocalDateTime.parse(scanner.nextLine(), formatacaoData);
                    System.out.println("Insira a data de chegada: Ex: dd/MM/yyyy HH:mm ");
                    LocalDateTime dataChegada = LocalDateTime.parse(scanner.nextLine(), formatacaoData);
                    System.out.println("Insira o trecho correspondente: Ex: BEL/CWB ");
                    String trecho = scanner.nextLine().toUpperCase();
                    System.out.println("Insira o valor da passagem: ");
                    BigDecimal valor = scanner.nextBigDecimal();
                    scanner.nextLine();

                    Passagem novaPassagem = new Passagem(dataPartida, dataChegada, valor);
                    passagemService.cadastrarPassagem(novaPassagem, trecho, usuario);

                    break;

                case "2":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA - EDITAR PASSAGEM");
                    System.out.println("-------------------------------");
                    System.out.println("Digite o codigo da passagem:");
                    String codigoPassagem = scanner.nextLine();
                    System.out.println("Insira a data de partida: Ex: dd/MM/yyyy HH:mm");
                    LocalDateTime novaDataPartida = LocalDateTime.parse(scanner.nextLine(), formatacaoData);
                    System.out.println("Insira a data de chegada:  Ex: dd/MM/yyyy HH:mm ");
                    LocalDateTime novaDataChegada = LocalDateTime.parse(scanner.nextLine(), formatacaoData);
                    System.out.println("Insira o trecho correspondente. Ex: BEL/CWB ");
                    String novoTrecho = scanner.nextLine().toUpperCase();
                    System.out.println("Insira o valor da passagem: ");
                    BigDecimal novoValor = scanner.nextBigDecimal();
                    scanner.nextLine();
                    System.out.println("Disponibilidade:\n[1] - disponivel\n[2] - indisponivel");
                    String disponivel = scanner.nextLine();
                    boolean novoDisponivel = false;

                    if (disponivel.equals("1")) {
                        novoDisponivel = true;
                    }

                    Passagem passagemEditada = new Passagem(codigoPassagem, novaDataPartida, novaDataChegada,
                            novoDisponivel, novoValor);
                    passagemService.editarPassagem(passagemEditada, novoTrecho, usuario);

                    break;
                case "3":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA - REMOVER PASSAGEM");
                    System.out.println("-------------------------------");
                    System.out.println("Digite a passagem a ser removida:");
                    Integer indexRemocaoPassagem = Integer.parseInt(scanner.nextLine());
                    companhiaService.deletarPassagem(indexRemocaoPassagem, usuario);
                    break;

                case "4":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA - PASSAGENS CADASTRADAS");
                    System.out.println("-------------------------------");
                    companhiaService.listarPassagensCadastradas(usuario);
                    break;

                case "5":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA - CADASTRAR TRECHO");
                    System.out.println("-------------------------------");
                    System.out.println("Digite a origem: Ex: BEL");
                    String cadastrarOrigem = scanner.nextLine().toUpperCase();
                    System.out.println("Digite o destino: Ex: CWB");
                    String cadastrarDestino = scanner.nextLine().toUpperCase();

                    Trecho trechoACadastrar = new Trecho(cadastrarOrigem, cadastrarDestino);
                    trechoService.criarTrecho(trechoACadastrar, usuario);
                    break;

                case "6":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA - EDITAR TRECHO");
                    System.out.println("-------------------------------");
                    System.out.println("Digite o index do trecho: Ex: 1");
                    Integer idEditarTrecho = Integer.parseInt(scanner.nextLine());
                    System.out.println("Digite a origem: Ex: BEL");
                    String editarOrigem = scanner.nextLine().toUpperCase();
                    System.out.println("Digite o destino: Ex: CWB");
                    String editarDestino = scanner.nextLine().toUpperCase();
                    Trecho editarTrecho = new Trecho(idEditarTrecho, editarOrigem, editarDestino);

                    trechoService.editarTrecho(idEditarTrecho, editarTrecho, usuario);
                    break;

                case "7":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA - REMOVER TRECHO");
                    System.out.println("-------------------------------");
                    System.out.println("Digite o id do trecho: Ex: 1");
                    Integer idTrecho = Integer.parseInt(scanner.nextLine());
                    trechoService.deletarTrecho(idTrecho, usuario);
                    break;

                case "8":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA - TRECHOS CADASTRADOS");
                    System.out.println("-------------------------------");
                    companhiaService.imprimirTrechosDaCompanhia(usuario);
                    break;

                case "9":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA - IMPRIMIR HISTORICO");
                    System.out.println("-------------------------------");
                    companhiaService.imprimirHistoricoDeVendas(usuario);
                    break;

                case "0":
                    break;

                default:
                    System.err.println("Opção inválida!");
            }
        }
    }

    private static void exibeMenuDeUsuarioComprador(Scanner scanner, PassagemService passagemService, Usuario usuario,
                                                    VendaService vendaService, CompradorService compradorService,
                                                    DateTimeFormatter formatacaoData) throws RegraDeNegocioException {
        String opcao = "";

        while (!opcao.equals("0")) {
            System.out.println("-------------------------------");
            System.out.println("BEM VINDO AO MENU DO COMPRADOR");
            System.out.println("-------------------------------");
            System.out.println("Escolha uma das opções abaixo:");
            System.out.println("[1] - Pesquisar Passagem");
            System.out.println("[2] - Comprar Passagem");
            System.out.println("[3] - Cancelar Compra");
            System.out.println("[4] - Histórico de Compras");
            System.out.println("[5] - Ultimas Passagens Cadastradas");
            System.out.println("[0] - Sair");

            opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.println(" ");
                    buscarTrecho(scanner, formatacaoData, passagemService);
                    break;

                case "2":
                    System.out.println("-------------------------------");
                    System.out.println("COMPRADOR - COMPRAR PASSAGEM");
                    System.out.println("-------------------------------");
                    System.out.println(" ");
                    menuDeCompra(scanner, compradorService, usuario);
                    break;

                case "3":
                    System.out.println("-------------------------------");
                    System.out.println("COMPRADOR - CANCELAR COMPRA");
                    System.out.println("-------------------------------");
                    System.out.println("Digite o codigo da compra: Ex: '01235'");
                    String codigo = scanner.nextLine();
                    vendaService.cancelarVenda(codigo);
                    break;

                case "4":
                    System.out.println("-------------------------------");
                    System.out.println("COMPRADOR - HISTÓRICO");
                    System.out.println("-------------------------------");
                    vendaService.getHistoricoVendasComprador(usuario);
                    break;

                case "5":
                    System.out.println("-------------------------------");
                    System.out.println("COMPRADOR - ULTIMAS PASSAGENS\n\t\t\tCADASTRADAS");
                    System.out.println("-------------------------------");
                    passagemService.listarUltimasPassagens();
                    break;

                case "0":
                    break;

                default:
                    System.err.println("Opção inválida!");
                    break;
            }
        }
    }

    private static void buscarTrecho(Scanner scanner, DateTimeFormatter formatacaoData,
                                     PassagemService passagemService) throws RegraDeNegocioException {
        String opcao = "";

        while (!opcao.equals("0")) {
            System.out.println("-------------------------------");
            System.out.println("COMPRADOR - PESQUISAR PASSAGEM");
            System.out.println("-------------------------------");
            System.out.println("Escolha uma das opções abaixo:");
            System.out.println("[1] - Data");
            System.out.println("[2] - Valor");
            System.out.println("[3] - Companhia Aerea");
            System.out.println("[0] - Sair");

            opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.println("-------------------------------");
                    System.out.println("COMPRADOR - PESQUISAR PASSAGEM\n\t\t\tPOR DATA");
                    System.out.println("-------------------------------");
                    System.out.println("[1] - Data de Partida");
                    System.out.println("[2] - Data de Chegada");
                    int tipoDeData = Integer.parseInt(scanner.nextLine());
                    System.out.println("Digite a data: dd/MM/yyyy HH:mm");
                    LocalDateTime data = LocalDateTime.parse(scanner.nextLine(), formatacaoData);

                    passagemService.listarPassagemPorData(data, tipoDeData);
                    break;

                case "2":
                    System.out.println("-------------------------------");
                    System.out.println("COMPRADOR - PESQUISAR PASSAGEM\n\t\t\tPOR VALOR");
                    System.out.println("-------------------------------");
                    System.out.println("Digite o limite maximo de valor: ");
                    BigDecimal valorMaximo = scanner.nextBigDecimal();
                    scanner.nextLine();

                    passagemService.listarPassagemPorValorMaximo(valorMaximo);
                    break;
                case "3":
                    System.out.println("-------------------------------");
                    System.out.println("COMPRADOR - PESQUISAR PASSAGEM\n\t\t\tPOR COMPANHIA AEREA");
                    System.out.println("-------------------------------");
                    System.out.println("Digite o nome da companhia aerea: ");
                    String nomeCompanhia = scanner.nextLine().toLowerCase();

                    passagemService.listarPassagemPorCompanhia(nomeCompanhia);
                    break;

                case "0":
                    break;

                default:
                    System.err.println("Opção inválida!");
                    break;
            }
        }
    }

    private static void menuDeCompra(Scanner scanner, CompradorService compradorService, Usuario usuario) throws RegraDeNegocioException {
        System.out.println("Digite o código da passagem: ");
        String codigoPassagem = scanner.nextLine();
        compradorService.comprarPassagem(codigoPassagem, usuario);
    }
}