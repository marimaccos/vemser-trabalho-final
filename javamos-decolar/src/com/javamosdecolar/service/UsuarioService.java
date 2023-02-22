package javamos_decolar.com.javamosdecolar.service;

import javamos_decolar.com.javamosdecolar.model.Companhia;
import javamos_decolar.com.javamosdecolar.model.Comprador;
import javamos_decolar.com.javamosdecolar.model.TipoUsuario;
import javamos_decolar.com.javamosdecolar.repository.CompanhiaRepository;
import javamos_decolar.com.javamosdecolar.repository.CompradorRepository;
import javamos_decolar.com.javamosdecolar.repository.UsuarioRepository;

import java.util.Optional;

public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService() {
        usuarioRepository = new UsuarioRepository();
    }

    public boolean entrarNoSistema(String login, String senha,
                                   CompradorRepository compradorDados, CompanhiaRepository companhiaRepository) {

        Optional<Comprador> compradorOpt = compradorDados.getListaDeComprador().stream()
                .filter(comprador -> comprador.getLogin() == login)
                .findAny();

        if (compradorOpt.isPresent()) {
            if (compradorOpt.get().getSenha() == senha) {
                return true;
            }
        }

        return false;
    }

    public boolean cadastrarUsuario(String login, String senha, String nome, TipoUsuario tipoUsuario,
                                    CompradorRepository compradorDados, CompanhiaRepository companhiaRepository) {

        if (login.isBlank() || senha.isBlank() || nome.isBlank()) {
            return  false;
        }

        if (tipoUsuario == TipoUsuario.COMPRADOR) {
            Comprador comprador = new Comprador(login, senha, nome, tipoUsuario);
            compradorDados.adicionar(comprador);

            return  true;
        } else {
            Companhia companhia = new Companhia(login, senha, nome, tipoUsuario);
            companhiaRepository.adicionar(companhia);

            return true;
        }
    }
}
