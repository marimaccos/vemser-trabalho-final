package javamos_decolar.com.javamosdecolar.repository;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.Comprador;
import javamos_decolar.com.javamosdecolar.model.TipoUsuario;
import javamos_decolar.com.javamosdecolar.model.Passagem;
import javamos_decolar.com.javamosdecolar.model.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompradorRepository {
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT seq_comprador.nextval mysequence from DUAL";
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

    public Comprador adicionar(Comprador comprador) throws DatabaseException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(conexao);
            comprador.setIdComprador(proximoId);

            String sql = "INSERT INTO COMPRADOR \n" +
                    "(ID_COMPRADOR, CPF, ID_USUARIO)\n" +
                    "VALUES(?, ?, ?)";

            PreparedStatement preparedStatement = conexao.prepareStatement(sql);

            preparedStatement.setInt(1, comprador.getIdComprador());
            preparedStatement.setString(2, comprador.getCpf());
            preparedStatement.setInt(3, comprador.getIdUsuario());

            preparedStatement.executeUpdate();

            return comprador;

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

    public Optional<Comprador> acharCompradorPorIdUsuario(Integer idUsuario) throws DatabaseException {
        Comprador compradorPesquisa = new Comprador();
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT ID_COMPRADOR, u.ID_USUARIO, LOGIN, NOME, CPF FROM COMPRADOR c \n" +
                    "INNER JOIN\n" +
                    "USUARIO u ON c.ID_USUARIO = u.ID_USUARIO WHERE c.ID_USUARIO = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setInt(1, idUsuario);

            ResultSet resultSet = statement.executeQuery(sql);

            compradorPesquisa.setIdComprador(resultSet.getInt("id_comprador"));
            compradorPesquisa.setIdUsuario(resultSet.getInt("id_usuario"));
            compradorPesquisa.setLogin(resultSet.getString("login"));
            compradorPesquisa.setNome(resultSet.getString("nome"));
            compradorPesquisa.setCpf(resultSet.getString("cpf"));

            if(resultSet.first()) {
                return Optional.of(compradorPesquisa);
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
