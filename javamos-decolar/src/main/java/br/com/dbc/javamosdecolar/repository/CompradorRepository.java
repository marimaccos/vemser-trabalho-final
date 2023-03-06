package br.com.dbc.javamosdecolar.repository;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.model.Comprador;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CompradorRepository {

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

    private Comprador getCompradorPorResultSet(ResultSet resultSet) throws SQLException {
        Comprador comprador = new Comprador();

        comprador.setIdComprador(resultSet.getInt("idComprador"));
        comprador.setCpf(resultSet.getString("cpf"));

        return comprador;
    }

    public Optional<Comprador> getCompradorPorID(Integer idComprador) throws DatabaseException {
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT *\n" +
                    "FROM COMPRADOR\n" +
                    "WHERE idComprador = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idComprador);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Comprador compradorEncontrado = getCompradorPorResultSet(resultSet);
                return Optional.of(compradorEncontrado);
            } else {
                return Optional.empty();
            }

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

    public Comprador criarComprador(Comprador comprador) throws DatabaseException {
        Connection connection = null;
        try {
            connection = ConexaoBancoDeDados.getConnection();

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

    public List<Comprador> listaCompradores() throws DatabaseException {
        List<Comprador> compradores = new ArrayList<Comprador>();
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM COMPRADOR";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Comprador comprador = getCompradorPorResultSet(resultSet);
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

    /*public Comprador editarComprador(Integer idComprador, Comprador comprador) throws DatabaseException {
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            String sql = "UPDATE COMPRADOR\n" +
                    "SET cpf = ?\n" +
                    "WHERE idComprador = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, comprador.getCpf());
            preparedStatement.setInt(2, idComprador);

            preparedStatement.executeUpdate();

            return comprador;

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
    }*/

    /*public Optional<Comprador> acharCompradorPorIdUsuario(Integer idUsuario) throws DatabaseException {
        Comprador compradorPesquisa = new Comprador();
        Connection connection = null;
        try{
            connection = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT c.id_comprador, c.cpf, c.id_usuario\n" +
                    "FROM COMPRADOR c \n" +
                    "WHERE c.id_usuario = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

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
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/
}
