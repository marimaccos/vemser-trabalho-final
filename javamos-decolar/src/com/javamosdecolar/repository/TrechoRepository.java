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
            String sql = "SELECT seq_trecho.nextval mysequence FROM TRECHO";
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

            PreparedStatement prepareStatement = connection.prepareStatement(sql);

            prepareStatement.setInt(1, trecho.getIdTrecho());
            prepareStatement.setString(2, trecho.getOrigem());
            prepareStatement.setString(3, trecho.getDestino());
            prepareStatement.setInt(4, trecho.getCompanhia().getIdCompanhia());

            int res = prepareStatement.executeUpdate();
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

            String sql = "SELECT * FROM TRECHO";

            // Executa-se a consulta
            ResultSet resultSet = statement.executeQuery(sql);

            // TODO : finalizar implementação
            while (resultSet.next()) {
                /*Trecho trecho = ;
                trechos.add(trecho);*/
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
        // TODO
        return false;
    }

    @Override
    public boolean remover(Integer id) throws DatabaseException {
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM TRECHO WHERE ID_TRECHO = ?";

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

    public Optional<Trecho> buscarTrecho(String origem, String destino, Companhia companhia) throws DatabaseException {
        // TO-DO
        return null;
    }

    public Optional<Trecho> buscarTrechoPorId(Integer idTrecho) throws DatabaseException {
        //TO-DO
        return null;
    }

    private Trecho getTrechoFromResultSet(ResultSet res) throws SQLException {
        // TODO : pegar id companhia para colocar no trecho
        /*Trecho trecho = new Trecho();

        trecho.setIdTrecho(res.getInt("id_trecho"));
        trecho.setOrigem(res.getString("origem"));
        trecho.setDestino(res.getString("destino"));
        trecho.setCompanhia(res.getInt("id_companhia"));*/

        return null;
    }
}
