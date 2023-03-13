package br.com.dbc.javamosdecolar.repository;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.model.Companhia;
import br.com.dbc.javamosdecolar.model.Trecho;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrechoRepository implements RepositoryCRUD<Trecho, Integer> {

    private final ConexaoBancoDeDados conexaoBancoDeDados;
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT seq_trecho.nextval mysequence from DUAL";
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

    @Override
    public Trecho create(Trecho trecho) throws DatabaseException {
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(connection);
            trecho.setIdTrecho(proximoId);

            String sql = "INSERT INTO TRECHO\n" +
                    "(id_trecho, origem, destino, id_companhia)\n" +
                    "VALUES(?, UPPER(?), UPPER(?), ?)\n";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, trecho.getIdTrecho());
            preparedStatement.setString(2, trecho.getOrigem());
            preparedStatement.setString(3, trecho.getDestino());
            preparedStatement.setInt(4, trecho.getCompanhia().getIdCompanhia());

            preparedStatement.executeUpdate();

            return trecho;

        } catch (SQLException e) {
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
    }

    @Override
    public boolean update(Integer id, Trecho trecho) throws DatabaseException {
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE TRECHO SET ");
            sql.append(" origem = ?,");
            sql.append(" destino = ?\n");
            sql.append(" WHERE id_trecho = ? ");

            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());

            preparedStatement.setString(1, trecho.getOrigem());
            preparedStatement.setString(2, trecho.getDestino());
            preparedStatement.setInt(3, id);

            // Executa-se a consulta
            int res = preparedStatement.executeUpdate();
            return res > 0;

        } catch (SQLException e) {
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
    }

    @Override
    public boolean delete(Integer id) throws DatabaseException {
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM TRECHO WHERE id_trecho = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            // Executa-se a consulta
            int res = preparedStatement.executeUpdate();
            return res > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Trecho conectado com outras entidades não poed ser deletado!");

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Trecho> getAll() throws DatabaseException {
        List<Trecho> trechos = new ArrayList<>();
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();
            Statement statement = connection.createStatement();

            String sql = "SELECT t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM TRECHO t\n" +
                    "INNER JOIN COMPANHIA c ON t.id_companhia = c.id_companhia\n";

            // Executa-se a consulta
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Trecho trecho = getByResultSet(resultSet);
                trechos.add(trecho);
            }
            return trechos;

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
    }

    public Optional<Trecho> getById(Integer idTrecho) throws DatabaseException {
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            String sql = "SELECT t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM TRECHO t\n" +
                    "INNER JOIN COMPANHIA c ON t.id_companhia = c.id_companhia\n" +
                    "WHERE t.id_trecho = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, idTrecho);

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Trecho trecho = getByResultSet(resultSet);
                return Optional.of(trecho);
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
    }

    public Optional<Trecho> getOne(String origem, String destino, Companhia companhia) throws DatabaseException {
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            String sql = "SELECT t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM TRECHO t\n" +
                    "INNER JOIN COMPANHIA c ON t.id_companhia = c.id_companhia\n" +
                    "WHERE t.origem = UPPER(?) AND t.destino = UPPER(?) AND t.id_companhia = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, origem);
            preparedStatement.setString(2, destino);
            preparedStatement.setInt(3, companhia.getIdCompanhia());

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Trecho trecho = getByResultSet(resultSet);
                return Optional.of(trecho);
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
    }

    public List<Trecho> getByCompanhia(Integer idCompanhia) throws DatabaseException {
        List<Trecho> trechos = new ArrayList<>();
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            String sql = "SELECT t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM TRECHO t\n" +
                    "INNER JOIN COMPANHIA c ON t.id_companhia = c.id_companhia\n" +
                    "WHERE C.id_companhia = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, idCompanhia);

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Trecho trecho = getByResultSet(resultSet);
                trechos.add(trecho);
            }
            return trechos;

        } catch (SQLException e) {
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
    }

    private Trecho getByResultSet(ResultSet resultSet) throws SQLException {

        // Retira os dados necessários da companhia para serem usados no trecho
        Companhia companhia = new Companhia();
        companhia.setIdCompanhia(resultSet.getInt("id_companhia"));
        companhia.setNomeFantasia(resultSet.getString("nome_fantasia"));

        Trecho trecho = new Trecho();
        trecho.setIdTrecho(resultSet.getInt("id_trecho"));
        trecho.setOrigem(resultSet.getString("origem"));
        trecho.setDestino(resultSet.getString("destino"));
        trecho.setCompanhia(companhia);

        return trecho;
    }
}
