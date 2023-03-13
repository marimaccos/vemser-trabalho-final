package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.DatabaseException;

import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Usuario;
import br.com.dbc.javamosdecolar.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    public Usuario create(Usuario usuario) throws RegraDeNegocioException {
        try{
            if (usuarioRepository.getByLogin(usuario.getLogin()).isEmpty()) {
                emailService.sendEmail(usuario);
                return usuarioRepository.create(usuario);

            } else {
                throw new RegraDeNegocioException("Não foi possível concluir o cadastro.");
            }

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante o cadastro");
        }
    }

    public Usuario update(Integer id, Usuario usuario) throws RegraDeNegocioException {
        try {
            usuarioRepository.getById(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado!"));
            usuarioRepository.update(id, usuario);

            usuario.setIdUsuario(id);

            return usuario;

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Ocorreu um problema durante a edição do cadastro.");
        }
    }

    public void delete(Integer idUsuario) throws RegraDeNegocioException {
        try {
            usuarioRepository.getById(idUsuario)
                    .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado!"));
            usuarioRepository.delete(idUsuario);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Ocorreu um problema durante a edição do cadastro.");
        }
    }
}
