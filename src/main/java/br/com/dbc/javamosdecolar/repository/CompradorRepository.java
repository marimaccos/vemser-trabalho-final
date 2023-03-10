package br.com.dbc.javamosdecolar.repository;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.model.Comprador;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CompradorRepository {

    private final ConexaoBancoDeDados conexaoBancoDeDados;
    public Integer getProximoId(Connection connection) throws DatabaseException {
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

    public Comprador create(Comprador comprador) throws DatabaseException {
        Connection connection = null;
        try {
            connection = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(connection);
            comprador.setIdComprador(proximoId);

            String sql = "INSERT INTO COMPRADOR \n" +
                    "(ID_COMPRADOR, CPF, ID_USUARIO)\n" +
                    "VALUES(?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, comprador.getIdComprador());
            preparedStatement.setString(2, comprador.getCpf());
            preparedStatement.setInt(3, comprador.getIdUsuario());

            preparedStatement.executeUpdate();

            return comprador;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getCause());
        } finally {
            try{
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Comprador> getAll() throws DatabaseException {
        List<Comprador> compradores = new ArrayList<Comprador>();
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();
            Statement statement = connection.createStatement();

            String sql = "SELECT c.ID_COMPRADOR, c.CPF, c.ID_USUARIO, u.LOGIN, u.SENHA, u.NOME, u.ATIVO \n" +
                    "FROM COMPRADOR c \n" +
                    "INNER JOIN USUARIO u \n" +
                    "ON c.ID_USUARIO = u.ID_USUARIO";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Comprador comprador = getByResultSet(resultSet);
                compradores.add(comprador);
            }

            return compradores;


        } catch (SQLException e) {
            throw new DatabaseException(e.getCause());

        } finally {
            try{
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Optional<Comprador> getById(Integer idComprador) throws DatabaseException {
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            String sql = "SELECT c.ID_COMPRADOR, c.CPF, c.ID_USUARIO, u.LOGIN, u.SENHA, u.NOME, u.ATIVO \n" +
                    "FROM COMPRADOR c \n" +
                    "INNER JOIN USUARIO u \n" +
                    "ON u.ID_USUARIO = c.ID_USUARIO\n" +
                    "WHERE c.ID_COMPRADOR = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idComprador);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Comprador compradorEncontrado = getByResultSet(resultSet);
                return Optional.of(compradorEncontrado);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getCause());

        } finally {
            try{
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Comprador getByResultSet(ResultSet resultSet) throws SQLException {
        Comprador comprador = new Comprador();

        comprador.setIdComprador(resultSet.getInt("id_comprador"));
        comprador.setCpf(resultSet.getString("cpf"));
        comprador.setIdUsuario(resultSet.getInt("id_usuario"));
        comprador.setNome(resultSet.getString("nome"));
        comprador.setLogin(resultSet.getString("login"));
        comprador.setSenha(resultSet.getString("senha"));

        int ativo = resultSet.getInt("ativo");

        if(ativo == 1) {
            comprador.setAtivo(true);
        } else {
            comprador.setAtivo(false);
        }

        return comprador;
    }
}
