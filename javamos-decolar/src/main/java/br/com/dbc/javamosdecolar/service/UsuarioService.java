package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.model.Companhia;
import br.com.dbc.javamosdecolar.model.Comprador;
import br.com.dbc.javamosdecolar.model.Usuario;
import br.com.dbc.javamosdecolar.repository.CompanhiaRepository;
import br.com.dbc.javamosdecolar.repository.CompradorRepository;
import br.com.dbc.javamosdecolar.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private CompanhiaRepository companhiaRepository;
    private CompradorRepository compradorRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, CompanhiaRepository companhiaRepository, CompradorRepository compradorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.companhiaRepository = companhiaRepository;
        this.compradorRepository = compradorRepository;
    }

    public void criarUsuarioComprador(Usuario usuario, String cpf) throws RegraDeNegocioException {
        try {

            if(usuario.getNome().isBlank() || usuario.getLogin().isBlank() ||
                    usuario.getNome().isBlank() || usuario.getSenha().isBlank()) {
                throw new RegraDeNegocioException("Os campos não podem ser nulos!");
            }

            if(cpf.length() != 11) {
                throw new RegraDeNegocioException("CPF no formato incorreto!");
            }

            if(usuario.getNome().length() > 100) {
                throw new RegraDeNegocioException("Nome tem que ter até 100 caracteres");
            }

            if(usuario.getSenha().length() > 20) {
                throw new RegraDeNegocioException("Senha tem que ter até 20 caracteres");
            }

            if(usuario.getLogin().length() > 20) {
                throw new RegraDeNegocioException("Login tem que ter até 20 caracteres");
            }

            // verifica se login já foi utilizado
            Optional<Usuario> usuarioEncontrado = usuarioRepository.buscaUsuarioPeloLogin(usuario.getLogin());

            if(usuarioEncontrado.isPresent()) {
                throw new RegraDeNegocioException("Login já existe!");
            }

            Usuario usuarioCriado = usuarioRepository.adicionar(usuario);
            Comprador comprador = new Comprador(usuarioCriado.getIdUsuario(), usuarioCriado.getLogin(),
                    usuarioCriado.getNome(), usuarioCriado.getSenha(), usuarioCriado.getTipoUsuario(),
                    cpf);
            Comprador compradorAdicionado = compradorRepository.criarComprador(comprador);
            System.out.println("Comprador adicinado com sucesso! " + compradorAdicionado);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação de usuario.");
        }
    }

    public void criarUsuarioCompanhia(Usuario usuario, String cnpj, String nomeFantasia) throws RegraDeNegocioException {
        try {

            if(usuario.getNome().isBlank() || usuario.getLogin().isBlank() ||
                    usuario.getNome().isBlank() || usuario.getSenha().isBlank() || nomeFantasia.isBlank()) {
                throw new RegraDeNegocioException("Os campos não podem ser nulos!");
            }

            if(cnpj.length() != 14) {
                throw new RegraDeNegocioException("CNPJ no formato incorreto!");
            }

            if(usuario.getNome().length() > 100) {
                throw new RegraDeNegocioException("Nome tem que ter até 100 caracteres");
            }

            if(usuario.getSenha().length() > 20) {
                throw new RegraDeNegocioException("Senha tem que ter até 20 caracteres");
            }

            if(usuario.getLogin().length() > 20) {
                throw new RegraDeNegocioException("Login tem que ter até 20 caracteres");
            }

            if(nomeFantasia.length() > 100) {
                throw new RegraDeNegocioException("Nome Fantasia tem que ter até 100 caracteres");
            }

            // verifica se login já foi utilizado
            Optional<Usuario> usuarioEncontrado = usuarioRepository.buscaUsuarioPeloLogin(usuario.getLogin());

            if(usuarioEncontrado.isPresent()) {
                throw new RegraDeNegocioException("Login já existe!");
            }

            Usuario usuarioCriado = usuarioRepository.adicionar(usuario);
            Companhia companhia = new Companhia(usuarioCriado.getIdUsuario(), usuarioCriado.getLogin(),
                    usuarioCriado.getNome(), usuarioCriado.getSenha(), usuarioCriado.getTipoUsuario(),
                    cnpj, nomeFantasia);
            Companhia companhiaAdicionada = companhiaRepository.adicionar(companhia);
            System.out.println("Companhia adicinada com sucesso! " + companhiaAdicionada);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação do usuario");
        }
    }

    public Optional<Usuario> entrarComUsuarioExistente(String login, String senha) throws RegraDeNegocioException {
        try {
            Optional<Usuario> usuario = usuarioRepository.buscaUsuarioPeloLogin(login);
            if (usuario.isEmpty()) {
                throw new RegraDeNegocioException("Usuário não encontrado!");
            } else {
                if (!usuario.get().getSenha().equals(senha)) {
                    throw new RegraDeNegocioException("Senha inválida!");
                } else {
                    System.out.println("Login realizado.");
                    return usuario;
                }
            }

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante o login.");
        }
    }
}
