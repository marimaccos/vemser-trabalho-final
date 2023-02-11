package javamos_decolar;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CompanhiaDados companhiaDados = new CompanhiaDados();
        CompradorDados compradorDados = new CompradorDados();
        VendaDados vendaDados = new VendaDados();
        TrechoDados trechoDados = new TrechoDados();
        PassagemDados passagemDados = new PassagemDados();
        boolean usuarioLogado = false;

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
                    System.out.println(usuarioLogado);
                    break;
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

    private static boolean entrarComUsuarioExistente(Scanner scanner, CompanhiaDados companhiaDados, CompradorDados compradorDados) {
        System.out.println("LOGIN");
        System.out.println("Insira o tipo de usuário: \n[1] - Companhia\n[2] - Comprador");
        String tipo = scanner.nextLine();
        System.out.print("Digite seu login: ");
        String login = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        if (tipo.equals("1")) {
            Optional<Comprador> compradorEncontrado = compradorDados.buscaCompradorPorLogin(login);

            if (compradorEncontrado.isEmpty()) {
                System.err.println("Login inválido.");

                return false;
            } else {
                if (!compradorEncontrado.get().getSenha().equals(senha)) {
                    System.err.println("Senha inválida.");

                    return false;
                } else {
                    System.out.println("Login realizado.");

                    return true;
                }
            }
        } else {
            Optional<Companhia> companhiaEncontrada = companhiaDados.buscaCompanhiaPorLogin(login);

            if (companhiaEncontrada.isEmpty()) {
                System.err.println("Login inválido.");

                return false;
            } else {
                if (!companhiaEncontrada.get().getSenha().equals(senha)) {
                    System.err.println("Senha inválida.");

                    return false;
                } else {
                    System.out.println("Login realizado.");

                    return true;
                }
            }

        }
    }
}

