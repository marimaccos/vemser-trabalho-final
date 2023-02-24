package javamos_decolar.com.javamosdecolar.repository;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository{
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT seq_usuario.nextval mysequence from DUAL";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException(e.getCause());
        }
    }

    public Usuario adicionar(Usuario usuario) throws DatabaseException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(conexao);
            usuario.setIdUsuario(proximoId);

            String sql = "INSERT INTO USUARIO \n" +
                    "(ID_USUARIO, LOGIN, SENHA, NOME, TIPO)\n" +
                    "VALUES(?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = conexao.prepareStatement(sql);

            preparedStatement.setInt(1, usuario.getIdUsuario());
            preparedStatement.setString(2, usuario.getLogin());
            preparedStatement.setString(3, usuario.getSenha());
            preparedStatement.setString(4, usuario.getNome());
            preparedStatement.setInt(5, usuario.getTipoUsuario().getTipo());

            int res = preparedStatement.executeUpdate();
            System.out.println("adicionarComprador.res=" + res);
            return usuario;

        } catch (SQLException e) {
            throw new DatabaseException(e.getCause());
        } finally {
            try{
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Optional<Usuario> buscaUsuarioPeloLogin(String login) throws DatabaseException {
        Usuario usuarioPesquisa = new Usuario();
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM USUARIO WHERE login = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setString(1, login);

            ResultSet resultSet = statement.executeQuery(sql);

            usuarioPesquisa.setIdUsuario(resultSet.getInt("id_usuario"));
            usuarioPesquisa.setLogin(resultSet.getString("login"));
            usuarioPesquisa.setSenha(resultSet.getString("senha"));
            usuarioPesquisa.setNome(resultSet.getString("nome"));
            usuarioPesquisa.setTipoUsuario((TipoUsuario) resultSet.getObject("tipo"));

            if(resultSet.first()) {
                return Optional.of(usuarioPesquisa);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getCause());
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
