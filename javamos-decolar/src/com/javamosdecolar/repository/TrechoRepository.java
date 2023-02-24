package javamos_decolar.com.javamosdecolar.repository;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.Trecho;
import javamos_decolar.com.javamosdecolar.model.Companhia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrechoRepository implements Repository<Trecho, Integer> {

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
    public Trecho adicionar(Trecho trecho) throws DatabaseException {
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(connection);
            trecho.setIdTrecho(proximoId);

            String sql = "INSERT INTO TRECHO\n" +
                    "(id_trecho, origem, destino, id_companhia)\n" +
                    "VALUES(?, ?, ?, ?)\n";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, trecho.getIdTrecho());
            preparedStatement.setString(2, trecho.getOrigem());
            preparedStatement.setString(3, trecho.getDestino());
            preparedStatement.setInt(4, trecho.getCompanhia().getIdCompanhia());

            int res = preparedStatement.executeUpdate();
            System.out.println("adicionarTrecho.res=" + res);
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
    public List<Trecho> listar() throws DatabaseException {
        List<Trecho> trechos = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();
            Statement statement = connection.createStatement();

            String sql = "SELECT t.id_trecho, t.origem, t.destino,\n" +
                    "c.nome_fantasia AS nome_companhia\n" +
                    "FROM TRECHO t\n" +
                    "INNER JOIN COMPANHIA c ON t.id_companhia = c.id_companhia\n";

            // Executa-se a consulta
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Trecho trecho = getTrechoPorResultSet(resultSet);
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

    @Override
    public boolean editar(Integer id, Trecho trecho) throws DatabaseException {
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

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
            System.out.println("editarTrecho.res=" + res);

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
    public boolean remover(Integer id) throws DatabaseException {
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM TRECHO WHERE id_trecho = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            // Executa-se a consulta
            int res = preparedStatement.executeUpdate();
            System.out.println("removerTrechoPorId.res=" + res);

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

    public Optional<Trecho> getTrecho(String origem, String destino, Companhia companhia) throws DatabaseException {
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT *\n" +
                    "FROM TRECHO\n" +
                    "WHERE origem = ? AND destino = ? AND id_companhia = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, origem);
            preparedStatement.setString(2, destino);
            preparedStatement.setInt(3, companhia.getIdCompanhia());

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.first()) {
                Trecho trecho = getTrechoPorResultSet(resultSet);
                return Optional.of(trecho);
            } else {
                return Optional.empty();
            }

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

    public Optional<Trecho> getTrechoPorId(Integer idTrecho) throws DatabaseException {
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT *\n" +
                    "FROM TRECHO\n" +
                    "WHERE id_trecho = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, idTrecho);

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.first()) {
                Trecho trecho = getTrechoPorResultSet(resultSet);
                return Optional.of(trecho);
            } else {
                return Optional.empty();
            }

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

    private Trecho getTrechoPorResultSet(ResultSet resultSet) throws SQLException {

        // Retira os dados necessários da companhia para serem usados no trecho
        Companhia companhia = new Companhia();
        companhia.setNomeFantasia(resultSet.getString("nome_fantasia"));
        companhia.setIdCompanhia(resultSet.getInt("id_companhia"));

        Trecho trecho = new Trecho();
        trecho.setIdTrecho(resultSet.getInt("id_trecho"));
        trecho.setOrigem(resultSet.getString("origem"));
        trecho.setDestino(resultSet.getString("destino"));
        trecho.setCompanhia(companhia);

        return trecho;
    }

    public Optional<Object> buscarTrechosPorCompanhia(Integer idCompanhia) throws DatabaseException {
        //TO-DO
        return null;
    }
}
