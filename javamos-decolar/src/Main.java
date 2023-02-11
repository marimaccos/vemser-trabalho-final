package javamos_decolar;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CompanhiaDados companhiaDados = new CompanhiaDados();
        CompradorDados compradorDados = new CompradorDados();
        VendaDados vendaDados = new VendaDados();
        TrechoDados trechoDados = new TrechoDados();
        PassagemDados passagemDados = new PassagemDados();
        Usuario usuarioLogado = null;

        Scanner scanner = new Scanner(System.in);
        Integer opcao = 0;

        final DateTimeFormatter FORMATACAO_DATA = DateTimeFormatter.ofPattern("dd-MM-yyyy");


        // CLASSES PARA TESTARMOS

        Companhia companhiaTeste = new Companhia("cp", "cp", "cp", Tipo.COMPANHIA, "123");
        Comprador compradorTeste = new Comprador("te", "te", "te", Tipo.COMPRADOR, "123");
        Trecho trechoTeste = new Trecho("FOR", "SP", companhiaTeste);
        Passagem passagemTeste = new Passagem(LocalDate.now(),
                LocalDate.now(), trechoTeste, true, BigDecimal.valueOf(2.0));
        Passagem passagemTeste2 = new Passagem(LocalDate.now(), LocalDate.now(), trechoTeste,
                true, BigDecimal.valueOf(2.0));
//        Venda vendaTeste = new Venda();

        passagemDados.adicionar(passagemTeste);
        passagemDados.adicionar(passagemTeste2);
        companhiaTeste.getPassagensCadastradas().add(passagemTeste);
        companhiaTeste.getPassagensCadastradas().add(passagemTeste2);
        companhiaDados.adicionar(companhiaTeste);
        compradorDados.adicionar(compradorTeste);
        companhiaTeste.criarTrecho(trechoTeste, trechoDados);
//        vendaTeste.efetuarVenda(passagemTeste,compradorTeste,companhiaTeste,vendaDados);

        // MENU
        System.out.println("-------------------------------");
        System.out.println("BEM-VINDO AO SISTEMA JAVAMOS DECOLAR!");
        while (opcao != 3) {
            System.out.println("-------------------------------");
            System.out.println("\t\tMENU PRINCIPAL");
            System.out.println("-------------------------------");
            System.out.println("[1] - Cadastrar Usuário\n" +
                    "[2] - Entrar com Usuário Existente\n" +
                    "[0] - Sair");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarUsuario(scanner, companhiaDados, compradorDados);
                    break;
                case 2:
                    usuarioLogado = entrarComUsuarioExistente(scanner, companhiaDados, compradorDados);
                    if (usuarioLogado instanceof Companhia) {
                        exibeMenuDeUsuarioCompanhia(scanner, (Companhia) usuarioLogado, passagemDados,
                                trechoDados, FORMATACAO_DATA);
                        break;
                    } else if (usuarioLogado instanceof Comprador) {
                        exibeMenuDeUsuarioComprador(scanner, (Comprador) usuarioLogado, passagemDados,
                                trechoDados, FORMATACAO_DATA, companhiaDados, vendaDados);
                        break;
                    } else {
                        break;
                    }
                case 0:
                    break;
                default:
                    System.err.println("Opção inválida.");
                    break;
            }
        }

    }

    private static void cadastrarUsuario(Scanner scanner, CompanhiaDados companhiaDados, CompradorDados compradorDados) {
        System.out.println("-------------------------------");
        System.out.println("CADASTRAR USUÁRIO");
        System.out.println("-------------------------------");
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite seu login: ");
        String login = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();
        System.out.println("Digite o tipo de usuário: ");
        System.out.println("[1] - Companhia\n" +
                "[2] - Comprador");
        String tipo = scanner.nextLine();

        if (tipo.equals("1")) {
            System.out.print("Digite cnpj: ");
            String cnpj = scanner.nextLine();

            Companhia companhia = new Companhia(login, senha, nome, Tipo.COMPANHIA, cnpj);
            companhiaDados.adicionar(companhia);

        } else if (tipo.equals("2")) {
            System.out.print("Digite cpf: ");
            String cpf = scanner.nextLine();

            Comprador comprador = new Comprador(login, senha, nome, Tipo.COMPRADOR, cpf);
            compradorDados.adicionar(comprador);

        } else {
            System.err.println("Tipo inválido!");
        }
    }

    private static Usuario entrarComUsuarioExistente(Scanner scanner, CompanhiaDados companhiaDados,
                                                     CompradorDados compradorDados) {
        System.out.println("LOGIN");
        System.out.println("Insira o tipo de usuário: \n[1] - Companhia\n[2] - Comprador");
        String tipo = scanner.nextLine();
        System.out.print("Digite seu login: ");
        String login = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        if (tipo.equals("2")) {
            Optional<Comprador> compradorEncontrado = compradorDados.buscaCompradorPorLogin(login);

            if (compradorEncontrado.isEmpty()) {
                System.err.println("Login inválido.");

                return null;
            } else {
                if (!compradorEncontrado.get().getSenha().equals(senha)) {
                    System.err.println("Senha inválida.");

                    return null;
                } else {
                    System.out.println("Login realizado.");

                    return compradorEncontrado.get();
                }
            }
        } else if(tipo.equals("1")) {
            Optional<Companhia> companhiaEncontrada = companhiaDados.buscaCompanhiaPorLogin(login);

            if (companhiaEncontrada.isEmpty()) {
                System.err.println("Login inválido.");

                return null;
            } else {
                if (!companhiaEncontrada.get().getSenha().equals(senha)) {
                    System.err.println("Senha inválida.");

                    return null;
                } else {
                    System.out.println("Login realizado.");

                    return companhiaEncontrada.get();
                }
            }

        } else {
            System.err.println("Opção inválida!");

            return null;
        }
    }

    private static void exibeMenuDeUsuarioCompanhia(Scanner scanner, Companhia companhia,
                                                    PassagemDados passagemDados, TrechoDados trechoDados,
                                                    DateTimeFormatter formatacaoData) {
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
                    System.out.println("COMPANHIA -- CADASTRAR PASSAGEM");
                    System.out.println("-------------------------------");
                    System.out.print("Insira a data de partida: Ex: dd-MM-yyyy ");
                    LocalDate dataPartida = LocalDate.parse(scanner.nextLine(), formatacaoData);
                    System.out.print("Insira a data de chegada: Ex: dd-MM-yyyy");
                    LocalDate dataChegada = LocalDate.parse(scanner.nextLine(), formatacaoData);
                    System.out.print("Insira o trecho correspondente: Ex: BEL/CWB ");
                    String trecho = scanner.nextLine();
                    System.out.print("Insira o valor da passagem: ");
                    BigDecimal valor = BigDecimal.valueOf(Double.valueOf(scanner.nextLine()));
                    companhia.cadastrarPassagem(trecho, passagemDados, trechoDados, dataPartida, dataChegada,  valor);
                    break;

                case "2":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA -- EDITAR PASSAGEM");
                    System.out.println("-------------------------------");
                    System.out.println("Digite a passagem a ser editada:");
                    Integer indexEdicaoPassagem = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Insira a data de partida: Ex: dd-MM-yyyy ");
                    LocalDate novaDataPartida = LocalDate.parse(scanner.nextLine(), formatacaoData);
                    System.out.print("Insira a data de chegada:  Ex: dd-MM-yyyy ");
                    LocalDate novaDataChegada = LocalDate.parse(scanner.nextLine(), formatacaoData);
                    System.out.print("Insira o trecho correspondente. Ex: BEL/CWB ");
                    String novoTrecho = scanner.nextLine();
                    System.out.print("Insira o valor da passagem: ");
                    BigDecimal novoValor = BigDecimal.valueOf(Double.valueOf(scanner.nextLine()));
                    System.out.println("Disponibilidade:\n[1] - disponivel\n[2] - indisponivel");
                    String disponivel = scanner.nextLine();
                    boolean novoDisponivel = false;
                    if (disponivel.equals("1")) {
                        novoDisponivel = true;
                    }

                    String[] novoOrigemEDestino = novoTrecho.split("/");

                    Optional<Trecho> novoTrechoOptional = trechoDados.buscarTrecho(novoOrigemEDestino[0],
                            novoOrigemEDestino[1], companhia);

                    if (novoTrechoOptional.isPresent()) {
                        Passagem passagem = new Passagem(novaDataPartida, novaDataChegada,
                                novoTrechoOptional.get(), novoDisponivel, novoValor);
                        System.out.println("Passagem adicionada com sucesso!");
                    } else {
                        System.err.println("Trecho inválido!");
                    }

                    passagemDados.editar(indexEdicaoPassagem, new Passagem(novaDataPartida, novaDataChegada, novoTrechoOptional.get(),
                            novoDisponivel, novoValor));

                    break;
                case "3":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA -- REMOVER PASSAGEM");
                    System.out.println("-------------------------------");
                    System.out.println("Digite a passagem a ser removida:");
                    Integer indexRemocaoPassagem = scanner.nextInt();
                    scanner.nextLine();
                    companhia.deletarPassagem(indexRemocaoPassagem, passagemDados);
                    break;

                case "4":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA -- PASSAGENS CADASTRADAS");
                    System.out.println("-------------------------------");
                    List<Passagem> passagems = passagemDados.pegarPassagemPorCompanhia(companhia);
                    if (passagems.isEmpty()) {
                        System.out.println("Não há passagens para exibir.");
                    } else {
                        passagems.stream().forEach(System.out::println);
                    }
                    break;

                case "5":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA -- CADASTRAR TRECHO");
                    System.out.println("-------------------------------");
                    System.out.println("Digite a origem: Ex: BEL");
                    String cadastrarOrigem = scanner.nextLine();
                    System.out.println("Digite o destino: Ex: CWB");
                    String cadastrarDestino = scanner.nextLine();

                    if (companhia.criarTrecho(new Trecho(cadastrarOrigem, cadastrarDestino,
                            companhia), trechoDados)) {
                        System.out.println("Trecho criado!");
                    } else {
                        System.err.println("Trecho já cadastrado!");
                    }
                    break;

                case "6":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA -- EDITAR TRECHO");
                    System.out.println("-------------------------------");
                    System.out.println("Digite o index do trecho: Ex: 1");
                    Integer indexEditarTrecho = Integer.parseInt(scanner.nextLine());
                    System.out.println("Digite a origem: Ex: BEL");
                    String editarOrigem = scanner.nextLine();
                    System.out.println("Digite o destino: Ex: CWB");
                    String editarDestino = scanner.nextLine();

                    if (companhia.editarTrecho(indexEditarTrecho, new Trecho(editarOrigem, editarDestino,
                            companhia), trechoDados)) {
                        System.out.println("Trecho editado!");
                    } else {
                        System.err.println("Trecho já cadastrado!");
                    }
                    break;

                case "7":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA -- REMOVER TRECHO");
                    System.out.println("-------------------------------");
                    System.out.println("Digite o index do trecho: Ex: 1");
                    Integer indexDeletarTrecho = Integer.parseInt(scanner.nextLine());
                    companhia.deletarTrecho(indexDeletarTrecho, trechoDados);
                    break;

                case "8":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA -- TRECHOS CADASTRADOS");
                    System.out.println("-------------------------------");
                    for (int i = 0; i < companhia.getTrechosCadastrados().size(); i++) {
                        if (companhia.getTrechosCadastrados().get(i) != null) {
                            System.out.println("id= " + i + " | "
                                    + companhia.getTrechosCadastrados().get(i));
                        }
                    }
                    break;

                case "9":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA -- IMPRIMIR HISTORICO");
                    System.out.println("-------------------------------");
                    companhia.imprimirHistorico();
                    break;

                case "0":
                    break;

                default:
                    System.err.println("Opção inválida!");
            }
        }
    }

    private static void exibeMenuDeUsuarioComprador(Scanner scanner, Comprador comprador,
                                                    PassagemDados passagemDados, TrechoDados trechoDados,
                                                    DateTimeFormatter formatacaoData, CompanhiaDados companhiaDados,
                                                    VendaDados vendaDados) {
        String opcao = "";

        while (!opcao.equals("0")) {
            System.out.println("-------------------------------");
            System.out.println("BEM VINDO AO MENU DO COMPRADOR");
            System.out.println("-------------------------------");
            System.out.println("Escolha uma das opções abaixo:");
            System.out.println("[1] - Pesquisar Passagem");
            System.out.println("[2] - Comprar Passagem");
            System.out.println("[3] - Cancelar Compra");
            System.out.println("[4] - Histórico de Passagens");
            System.out.println("[5] - Ultimas Passagens Cadastradas");
            System.out.println("[0] - Sair");

            opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print(" ");
                    buscarTrecho(scanner, formatacaoData, passagemDados, companhiaDados);
                    break;

                case "2":
                    System.out.println("-------------------------------");
                    System.out.println("COMPRADOR -- COMPRAR PASSAGEM");
                    System.out.println("-------------------------------");
                    System.out.print("");
                    menuDeCompra(scanner, comprador, passagemDados, vendaDados);
                    break;

                case "3":
                    System.out.println("-------------------------------");
                    System.out.println("COMPRADOR -- CANCELAR COMPRA");
                    System.out.println("-------------------------------");
                    System.out.println("Digite o index da compra: Ex: 1");
                    Integer indexCancelarCompra = Integer.parseInt(scanner.nextLine());
                    if (indexCancelarCompra <= comprador.getHistoricoCompras().size()) {
                        comprador.getHistoricoCompras().get(indexCancelarCompra).setStatus(Status.CANCELADO);
                        System.out.println("Compra cancelada com sucesso!");
                    } else {
                        System.err.println("Index inválido!");
                    }
                    break;

                case "4":
                    System.out.println("-------------------------------");
                    System.out.println("COMPRADOR -- HISTÓRICO DE PASSAGENS");
                    System.out.println("-------------------------------");
                    System.out.print(" ");
                    comprador.imprimirHistorico();
                    break;
                case "5":
                    System.out.println("-------------------------------");
                    System.out.println("COMPRADOR -- ULTIMAS PASSAGENS\n\t\t\tCADASTRADAS");
                    System.out.println("-------------------------------");
                    System.out.print(" ");
                    passagemDados.listarDesc();
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
                                     PassagemDados passagemDados, CompanhiaDados companhiaDados) {
        String opcao = "";

        while (!opcao.equals("0")) {
            System.out.println("-------------------------------");
            System.out.println("COMPRADOR -- PESQUISAR PASSAGEM");
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
                    System.out.println("Digite a data: dd-MM-yyyy ");
                    LocalDate data = LocalDate.parse(scanner.nextLine(), formatacaoData);
                    if(tipoDeData == 1) {
                        passagemDados.pegarPassagemPorDataPartida(data).stream()
                                .forEach(System.out::println);
                    } else if (tipoDeData == 2) {
                        passagemDados.pegarPassagemPorDataChegada(data).stream()
                                .forEach(System.out::println);
                    } else {
                        System.err.println("Opção inválida!");
                    }
                    break;

                case "2":
                    System.out.println("-------------------------------");
                    System.out.println("COMPRADOR - PESQUISAR PASSAGEM\n\t\t\tPOR VALOR");
                    System.out.println("-------------------------------");
                    System.out.print(" ");
                    System.out.print("Digite o limite maximo de valor: ");

                    try {
                        BigDecimal valorMaximo = BigDecimal.valueOf(Double.parseDouble(scanner.nextLine()));
                        passagemDados.pegarPassagemPorValor(valorMaximo).stream().forEach(System.out::println);
                        break;
                    } catch (Exception e) {
                        System.err.println("Digite um valor válido!");
                        break;
                    }
                case "3":
                    System.out.println("-------------------------------");
                    System.out.println("COMPRADOR - PESQUISAR PASSAGEM\n\t\t\tPOR COMPANHIA AEREA");
                    System.out.println("-------------------------------");
                    System.out.println("Digite o nome da companhia aerea: ");
                    String nomeCompanhia = scanner.nextLine();
                    Optional<Companhia> companhia = companhiaDados.buscaCompanhiaPorNome(nomeCompanhia);
                    if(companhia.isEmpty()) {
                        System.err.println("Companhia não encontrada!");
                        break;
                    }
                    passagemDados.pegarPassagemPorCompanhia(companhia.get()).stream().forEach(System.out::println);
                    break;

                case "0":
                    break;

                default:
                    System.err.println("Opção inválida!");
                    break;
            }
        }
    }

    private static void menuDeCompra(Scanner scanner, Comprador comprador,
                                     PassagemDados passagemDados, VendaDados vendaDados) {
        System.out.println("Digite o código da passagem: ");
        String codigoPassagem = scanner.nextLine();
        comprador.comprarPassagem(codigoPassagem, passagemDados, vendaDados);
    }
}