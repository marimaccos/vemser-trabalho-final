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

        // MENU
        while (opcao != 3) {
            System.out.println("[1] - Cadastrar Usuário\n" +
                    "[2] - Entrar com Usuário Existente\n" +
                    "[3] - Sair");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarUsuario(scanner, companhiaDados, compradorDados);
                    break;
                case 2:
                    usuarioLogado = entrarComUsuarioExistente(scanner, companhiaDados, compradorDados);
                    if(usuarioLogado instanceof Companhia) {
                        exibeMenuDeUsuarioCompanhia(scanner, (Companhia) usuarioLogado, passagemDados, trechoDados);
                        break;
                    } else if(usuarioLogado instanceof Comprador) {
                        exibeMenuDeUsuarioComprador();
                        break;
                    } else {
                        break;
                    }
                case 3:
                    break;
                default:
                    System.err.println("Opção inválida.");
                    break;
            }
        }

    }

    private static void cadastrarUsuario(Scanner scanner, CompanhiaDados companhiaDados, CompradorDados compradorDados) {
        System.out.println("CADASTRAR USUÁRIO");
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

    private static Usuario entrarComUsuarioExistente(Scanner scanner, CompanhiaDados companhiaDados, CompradorDados compradorDados) {
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
        } else {
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

        }
    }

    private static void exibeMenuDeUsuarioCompanhia(Scanner scanner, Companhia companhia,
                                                    PassagemDados passagemDados, TrechoDados trechoDados) {
        String opcao = "";
        while (!opcao.equals("0")) {
            System.out.println("-------------------------------");
            System.out.println("BEM VINDO AO MENU DO COMPANHIA");
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

            final DateTimeFormatter FORMATACAO_DATA = DateTimeFormatter.ofPattern("dd-mm-yyyy");


            switch (opcao) {
                case "1":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA -- CADASTRAR PASSAGEM");
                    System.out.println("-------------------------------");
                    System.out.print("Insira a data de partida");
                    LocalDate dataPartida = LocalDate.parse(scanner.nextLine(), FORMATACAO_DATA);
                    System.out.print("Insira a data de partida");
                    LocalDate dataChegada = LocalDate.parse(scanner.nextLine(), FORMATACAO_DATA);
                    System.out.print("Insira o trecho correspondente. Ex: BEL/CWB");
                    String trecho = scanner.nextLine();
                    System.out.print("Insira o valor da passagem");
                    BigDecimal valor = BigDecimal.valueOf(Double.valueOf(scanner.nextLine()));

                    String[] origemEDestino = trecho.split("/");

                    Optional<Trecho> trechoOptional = trechoDados.buscarTrecho(origemEDestino[0],
                            origemEDestino[2], companhia);
                    if(trechoOptional.isPresent()) {
                        Passagem passagem = new Passagem(dataPartida, dataChegada,
                                trechoOptional.get(), true, valor);
                        passagemDados.adicionar(passagem);
                        System.out.println("Passagem adicionada com sucesso!");
                    } else {
                        System.err.println("Trecho inválido!");
                    }
                    break;
                case "2":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA -- EDITAR PASSAGEM");
                    System.out.println("-------------------------------");
                    List<Passagem> listaDePassagens = passagemDados.pegarPassagemPorCompanhia(companhia);
                    for (int i = 0; i < listaDePassagens.size(); i++) {
                        System.out.println(i + " - " + listaDePassagens.get(i));
                    }
                    System.out.println("Digite a passagem a ser editada:");
                    Integer index = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Insira a data de partida");
                    LocalDate novaDataPartida = LocalDate.parse(scanner.nextLine(), FORMATACAO_DATA);
                    System.out.print("Insira a data de partida");
                    LocalDate novaDataChegada = LocalDate.parse(scanner.nextLine(), FORMATACAO_DATA);
                    System.out.print("Insira o trecho correspondente. Ex: BEL/CWB");
                    String novoTrecho = scanner.nextLine();
                    System.out.print("Insira o valor da passagem");
                    BigDecimal novoValor = BigDecimal.valueOf(Double.valueOf(scanner.nextLine()));
                    System.out.println("Disponibilidade: 1 - true/2 - false");
                    String disponivel = scanner.nextLine();
                    boolean novoDisponivel = false;
                    if (disponivel.equals("1")) {
                        novoDisponivel = true;
                    }

                    String[] novoOrigemEDestino = novoTrecho.split("/");

                    Optional<Trecho> novoTrechoOptional = trechoDados.buscarTrecho(novoOrigemEDestino[0],
                            novoOrigemEDestino[2], companhia);
                    if(novoTrechoOptional.isPresent()) {
                        Passagem passagem = new Passagem(novaDataPartida, novaDataChegada,
                                novoTrechoOptional.get(), novoDisponivel, novoValor);
                        passagemDados.adicionar(passagem);
                        System.out.println("Passagem adicionada com sucesso!");
                    } else {
                        System.err.println("Trecho inválido!");
                    }

                    passagemDados.editar(index, new Passagem(novaDataPartida, novaDataChegada, novoTrechoOptional.get(),
                            novoDisponivel, novoValor));
                    break;
                case "3":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA -- REMOVER PASSAGEM");
                    System.out.println("-------------------------------");
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
                    String origem = scanner.nextLine();
                    System.out.println("Digite o destino: Ex: CWB");
                    String destino = scanner.nextLine();

                    if (companhia.criarTrecho(new Trecho(origem, destino, companhia), trechoDados)) {
                        System.out.println("Trecho criado");
                    } else {
                        System.err.println("Trecho já cadastrado");
                    }
                    break;
                case "6":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA -- EDITAR TRECHO");
                    System.out.println("-------------------------------");
                    break;
                case "7":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA -- REMOVER TRECHO");
                    System.out.println("-------------------------------");
                    break;
                case "8":
                    System.out.println("-------------------------------");
                    System.out.println("COMPANHIA -- TRECHOS CADASTRADOS");
                    System.out.println("-------------------------------");
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

    private static void exibeMenuDeUsuarioComprador() {
        //TO-DO: Menu do Comprador
    }

}

