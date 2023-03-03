package br.com.dbc.javamosdecolar.repository;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.model.Comprador;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
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

            String sql = "SELECT c.id_comprador, c.cpf, c.id_usuario\n" +
                    "FROM COMPRADOR c \n" +
                    "WHERE c.id_usuario = ?";

            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setInt(1, idUsuario);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                compradorPesquisa.setIdComprador(resultSet.getInt("id_comprador"));
                compradorPesquisa.setIdUsuario(resultSet.getInt("id_usuario"));
                compradorPesquisa.setCpf(resultSet.getString("cpf"));
                return Optional.of(compradorPesquisa);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
