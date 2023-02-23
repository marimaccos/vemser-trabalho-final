package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.Companhia;
import javamos_decolar.com.javamosdecolar.model.Comprador;
import javamos_decolar.com.javamosdecolar.model.Usuario;
import javamos_decolar.com.javamosdecolar.repository.CompanhiaRepository;
import javamos_decolar.com.javamosdecolar.repository.CompradorRepository;
import javamos_decolar.com.javamosdecolar.repository.UsuarioRepository;

import java.util.Optional;

public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private CompanhiaRepository companhiaRepository;
    private CompradorRepository compradorRepository;

    public UsuarioService() {
        usuarioRepository = new UsuarioRepository();
        companhiaRepository = new CompanhiaRepository();
        compradorRepository = new CompradorRepository();
    }

    public void criarUsuarioComprador(Usuario usuario, String cpf) {
        try {

            if(usuario.getNome().isBlank() || usuario.getLogin().isBlank() ||
                    usuario.getNome().isBlank() || usuario.getSenha().isBlank()) {
                throw new Exception("Os campos não podem ser nulos!");
            }

            if(cpf.length() != 11) {
                throw new Exception("CPF no formato incorreto!");
            }

            // verifica se login já foi utilizado
            Optional<Usuario> usuarioEncontrado = usuarioRepository.buscaUsuarioPeloLogin(usuario.getLogin());

            if(usuarioEncontrado.isPresent()) {
                throw new Exception("Login já existe!");
            }

            Usuario usuarioCriado = usuarioRepository.adicionar(usuario);
            Comprador comprador = new Comprador(usuarioCriado.getIdUsuario(), usuarioCriado.getLogin(),
                    usuarioCriado.getNome(), usuarioCriado.getSenha(), usuarioCriado.getTipoUsuario(),
                    cpf);
            Comprador compradorAdicionado = compradorRepository.adicionar(comprador);
            System.out.println("Comprador adicinado com sucesso! " + compradorAdicionado);
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }

    public void criarUsuarioCompanhia(Usuario usuario, String cnpj, String nomeFantasia) {
        try {

            if(usuario.getNome().isBlank() || usuario.getLogin().isBlank() ||
                    usuario.getNome().isBlank() || usuario.getSenha().isBlank() || nomeFantasia.isBlank()) {
                throw new Exception("Os campos não podem ser nulos!");
            }

            if(cnpj.length() != 11) {
                throw new Exception("CPF no formato incorreto!");
            }

            // verifica se login já foi utilizado
            Optional<Usuario> usuarioEncontrado = usuarioRepository.buscaUsuarioPeloLogin(usuario.getLogin());

            if(usuarioEncontrado.isPresent()) {
                throw new Exception("Login já existe!");
            }

            Usuario usuarioCriado = usuarioRepository.adicionar(usuario);
            Companhia companhia = new Companhia(usuarioCriado.getIdUsuario(), usuarioCriado.getLogin(),
                    usuarioCriado.getNome(), usuarioCriado.getSenha(), usuarioCriado.getTipoUsuario(),
                    cnpj, nomeFantasia);
            Companhia companhiaAdicionada = companhiaRepository.adicionar(companhia);
            System.out.println("Companhia adicinada com sucesso! " + companhiaAdicionada);
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }

    public Optional<Usuario> entrarComUsuarioExistente(String login, String senha) {
        try {
            Optional<Usuario> usuario = usuarioRepository.buscaUsuarioPeloLogin(login);
            if (usuario.isEmpty()) {
                throw new Exception("Usuário não encontrado!");
            } else {
                if (!usuario.get().getSenha().equals(senha)) {
                    throw new Exception("Senha inválida!");
                } else {
                    System.out.println("Login realizado.");
                    return usuario;
                }
            }

        } catch (DatabaseException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            return null;
        }
    }
}
