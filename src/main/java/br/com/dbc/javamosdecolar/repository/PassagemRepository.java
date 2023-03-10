package br.com.dbc.javamosdecolar.repository;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.model.Companhia;
import br.com.dbc.javamosdecolar.model.Passagem;
import br.com.dbc.javamosdecolar.model.Trecho;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PassagemRepository {

    private final ConexaoBancoDeDados conexaoBancoDeDados;
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT seq_passagem.nextval mysequence from DUAL";
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

    public Passagem create(Passagem passagem) throws DatabaseException {
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(connection);
            passagem.setIdPassagem(proximoId);

            String sql = "INSERT INTO PASSAGEM\n" +
                    "(id_passagem, codigo, data_partida, data_chegada, disponivel, valor, id_trecho, id_venda)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, null)\n";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, passagem.getIdPassagem());
            preparedStatement.setString(2, passagem.getCodigo());
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(passagem.getDataPartida()));
            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(passagem.getDataChegada()));
            preparedStatement.setBoolean(5, passagem.isDisponivel());
            preparedStatement.setBigDecimal(6, passagem.getValor());
            preparedStatement.setInt(7, passagem.getTrecho().getIdTrecho());

            preparedStatement.executeUpdate();
            return passagem;

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

    public boolean update(Integer id, Passagem passagem, Integer vendaId) throws DatabaseException {
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE PASSAGEM SET ");
            sql.append(" data_partida = ?,");
            sql.append(" data_chegada = ?,");
            sql.append(" disponivel = ?,");
            sql.append(" valor = ?,\n");
            sql.append(" id_venda = ?\n");
            sql.append(" WHERE id_passagem = ?");

            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());

            preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(passagem.getDataPartida()));
            preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(passagem.getDataChegada()));
            preparedStatement.setBoolean(3, passagem.isDisponivel());
            preparedStatement.setBigDecimal(4, passagem.getValor());
            if(vendaId != 0) {
                preparedStatement.setInt(5, vendaId);
            } else {
                preparedStatement.setObject(5, null);
            }
            preparedStatement.setInt(6, id);

            // Executa-se a consulta
            int res = preparedStatement.executeUpdate();
            return res > 0;

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

    public boolean delete(Integer id) throws DatabaseException {
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM PASSAGEM WHERE id_passagem = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

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

    public List<Passagem> getAll() throws DatabaseException {
        List<Passagem> passagens = new ArrayList<>();
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();
            Statement statement = connection.createStatement();

            String sql = "SELECT p.id_passagem, p.codigo, p.data_partida, p.data_chegada, p.disponivel, p.valor,\n" +
                    "t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM PASSAGEM p\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia  = t.id_companhia\n";

            // Executa-se a consulta
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Passagem passagem = getByResultSet(resultSet);
                passagens.add(passagem);
            }
            return passagens;

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

    public Optional<Passagem> getById(Integer id) throws DatabaseException {
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            String sql = "SELECT p.id_passagem, p.codigo, p.data_partida, p.data_chegada, p.disponivel, p.valor,\n" +
                    "t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM PASSAGEM p\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia  = t.id_companhia\n" +
                    "WHERE p.id_passagem = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Passagem passagem = getByResultSet(resultSet);
                return Optional.of(passagem);
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

    public List<Passagem> getByDataPartida(LocalDateTime dataPartida) throws DatabaseException {
        List<Passagem> passagens = new ArrayList<>();
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            String sql = "SELECT p.id_passagem, p.codigo, p.data_partida, p.data_chegada, p.disponivel, p.valor,\n" +
                    "t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM PASSAGEM p\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia  = t.id_companhia\n" +
                    "WHERE p.data_partida = ? AND p.disponivel = 1";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(dataPartida));

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Passagem passagem = getByResultSet(resultSet);
                passagens.add(passagem);
            }
            return passagens;

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

    public List<Passagem> getByDataChegada(LocalDateTime dataChegada) throws DatabaseException {
        List<Passagem> passagens = new ArrayList<>();
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            String sql = "SELECT p.id_passagem, p.codigo, p.data_partida, p.data_chegada, p.disponivel, p.valor,\n" +
                    "t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM PASSAGEM p\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia = t.id_companhia\n" +
                    "WHERE p.data_chegada = ?  AND p.disponivel = 1";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(dataChegada));

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Passagem passagem = getByResultSet(resultSet);
                passagens.add(passagem);
            }
            return passagens;

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

    public List<Passagem> getByValor(BigDecimal valorMaximo) throws DatabaseException {
        List<Passagem> passagens = new ArrayList<>();
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            String sql = "SELECT p.id_passagem, p.codigo, p.data_partida, p.data_chegada, p.disponivel, p.valor,\n" +
                    "t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM PASSAGEM p\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia  = t.id_companhia\n" +
                    "WHERE p.valor <= ?  AND p.disponivel = 1";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setBigDecimal(1, valorMaximo);

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Passagem passagem = getByResultSet(resultSet);
                passagens.add(passagem);
            }
            return passagens;

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

    public List<Passagem> getByCompanhia(Integer idCompanhia) throws DatabaseException {
        List<Passagem> passagens = new ArrayList<>();
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();

            String sql = "SELECT p.id_passagem, p.codigo, p.data_partida, p.data_chegada, p.disponivel, p.valor,\n" +
                    "t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM PASSAGEM p\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia  = t.id_companhia\n" +
                    "WHERE c.id_companhia = ?  AND p.disponivel = 1";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, idCompanhia);

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Passagem passagem = getByResultSet(resultSet);
                passagens.add(passagem);
            }
            return passagens;

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

    public List<Passagem> getUltimasPassagens() throws DatabaseException {
        List<Passagem> passagens = new ArrayList<>();
        Connection connection = null;

        try {
            connection = conexaoBancoDeDados.getConnection();
            Statement statement = connection.createStatement();

            // Seleciona as 5 últimas passagens adicionadas
            String sql = "SELECT *\n" +
                    "FROM (SELECT p.id_passagem, p.codigo, p.data_partida, p.data_chegada, p.disponivel, p.valor,\n" +
                    "t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM PASSAGEM p\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia = t.id_companhia\n" +
                    "WHERE p.disponivel = 1\n" +
                    "ORDER BY ID_PASSAGEM DESC) tables\n" +
                    "WHERE ROWNUM <= 5";

            // Executa-se a consulta
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Passagem passagem = getByResultSet(resultSet);
                passagens.add(passagem);
            }
            return passagens;

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

    private Passagem getByResultSet(ResultSet resultSet) throws SQLException {
        // Retira os dados necessários da companhia para serem usados no trecho
        Companhia companhia = new Companhia();
        companhia.setNomeFantasia(resultSet.getString("nome_fantasia"));
        companhia.setIdCompanhia(resultSet.getInt("id_companhia"));

        // Retira os dados necessários de trecho para serem usados na passagem
        Trecho trecho = new Trecho();
        trecho.setIdTrecho(resultSet.getInt("id_trecho"));
        trecho.setOrigem(resultSet.getString("origem"));
        trecho.setDestino(resultSet.getString("destino"));
        trecho.setCompanhia(companhia);

        Passagem passagem = new Passagem();
        passagem.setIdPassagem(resultSet.getInt("id_passagem"));
        passagem.setCodigo(resultSet.getString("codigo"));
        passagem.setDataPartida((resultSet.getTimestamp("data_partida").toLocalDateTime()));
        passagem.setDataChegada((resultSet.getTimestamp("data_chegada").toLocalDateTime()));
        passagem.setValor((resultSet.getBigDecimal("valor")));
        passagem.setTrecho(trecho);

        if (resultSet.getString("disponivel").equals("0")) {
            passagem.setDisponivel(false);

        } else if (resultSet.getString("disponivel").equals("1")) {
            passagem.setDisponivel(true);
        }

        return passagem;
    }
}
