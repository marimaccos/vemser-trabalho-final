package javamos_decolar.com.javamosdecolar.repository;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository implements Repository<Usuario, Integer>{

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        //TO-DO
        return null;
    }

    @Override
    public Usuario adicionar(Usuario usuario) throws DatabaseException {
        //TO-DO
        return null;
    }

    @Override
    public List<Usuario> listar() throws DatabaseException {
        //TO-DO
        return null;
    }

    @Override
    public boolean editar(Integer id, Usuario usuario) throws DatabaseException {
        //TO-DO
        return false;
    }

    @Override
    public boolean remover(Integer id) throws DatabaseException {
        //TO-DO
        return false;
    }

    public Optional<Usuario> buscaUsuarioPeloLogin(String login) throws DatabaseException {
        //TO-DO
        return null;
    }
}
