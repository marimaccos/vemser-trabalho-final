package javamos_decolar.com.javamosdecolar.repository;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.Passagem;
import javamos_decolar.com.javamosdecolar.model.Companhia;
import javamos_decolar.com.javamosdecolar.model.Trecho;
import javamos_decolar.com.javamosdecolar.model.Venda;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PassagemRepository implements Repository<Passagem, Integer> {

    @Override
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


    @Override
    public Passagem adicionar(Passagem passagem) throws DatabaseException {
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

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

            int res = preparedStatement.executeUpdate();
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

    @Override
    public List<Passagem> listar() throws DatabaseException {
        List<Passagem> passagens = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();
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
                Passagem passagem = getPassagemPorResultSet(resultSet);
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

    @Override
    public boolean editar(Integer id, Passagem passagem) throws DatabaseException {
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE PASSAGEM SET ");
            sql.append(" data_partida = ?,");
            sql.append(" data_chegada = ?,");
            sql.append(" disponivel = ?,");
            sql.append(" valor = ?\n");
            sql.append(" WHERE id_passagem = ? ");

            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());

            preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(passagem.getDataPartida()));
            preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(passagem.getDataChegada()));
            preparedStatement.setBoolean(3, passagem.isDisponivel());
            preparedStatement.setBigDecimal(4, passagem.getValor());
            preparedStatement.setInt(5, id);

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
    public boolean remover(Integer id) throws DatabaseException {
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

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

    private Passagem getPassagemPorResultSet(ResultSet resultSet) throws SQLException {
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

    public boolean editarDisponibilidadeDaPassagem(boolean disponivel, Passagem passagem) throws DatabaseException {

        Connection connection = null;

        Integer boolOracle = 0;

        if(disponivel) {
            boolOracle = 1;
        }

        try {
            connection = ConexaoBancoDeDados.getConnection();

            String sql = "UPDATE PASSAGEM SET " +
                    " disponivel = ? " +
                    " WHERE ID_PASSAGEM = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, boolOracle);
            preparedStatement.setInt(2, passagem.getIdPassagem());

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

    public Optional<Passagem> getPassagemPeloId(Integer id) throws DatabaseException {
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

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
                Passagem passagem = getPassagemPorResultSet(resultSet);
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

    public Optional<Passagem> getPassagemPorCodigo(String codigo) throws DatabaseException {
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT p.id_passagem, p.codigo, p.data_partida, p.data_chegada, p.disponivel, p.valor,\n" +
                    "t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM PASSAGEM p\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia = t.id_companhia\n" +
                    "WHERE p.codigo = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, codigo);

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Passagem passagem = getPassagemPorResultSet(resultSet);
                return Optional.of(passagem);
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

    public List<Passagem> getPassagemPorDataPartida(LocalDateTime dataPartida) throws DatabaseException {
        List<Passagem> passagens = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT p.id_passagem, p.codigo, p.data_partida, p.data_chegada, p.disponivel, p.valor,\n" +
                    "t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM PASSAGEM p\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia  = t.id_companhia\n" +
                    "WHERE p.data_partida = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(dataPartida));

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Passagem passagem = getPassagemPorResultSet(resultSet);
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

    public List<Passagem> getPassagemPorDataChegada(LocalDateTime dataChegada) throws DatabaseException {
        List<Passagem> passagens = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT p.id_passagem, p.codigo, p.data_partida, p.data_chegada, p.disponivel, p.valor,\n" +
                    "t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM PASSAGEM p\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia = t.id_companhia\n" +
                    "WHERE p.data_chegada = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(dataChegada));

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Passagem passagem = getPassagemPorResultSet(resultSet);
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

    public List<Passagem> getPassagemPorValor(BigDecimal valorMaximo) throws DatabaseException {
        List<Passagem> passagens = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT p.id_passagem, p.codigo, p.data_partida, p.data_chegada, p.disponivel, p.valor,\n" +
                    "t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM PASSAGEM p\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia  = t.id_companhia\n" +
                    "WHERE p.valor <= ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setBigDecimal(1, valorMaximo);

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Passagem passagem = getPassagemPorResultSet(resultSet);
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

    public List<Passagem> getPassagemPorCompanhia(Integer idCompanhia) throws DatabaseException {
        List<Passagem> passagens = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT p.id_passagem, p.codigo, p.data_partida, p.data_chegada, p.disponivel, p.valor,\n" +
                    "t.id_trecho, t.origem, t.destino,\n" +
                    "c.id_companhia, c.nome_fantasia\n" +
                    "FROM PASSAGEM p\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia  = t.id_companhia\n" +
                    "WHERE c.id_companhia = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, idCompanhia);

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Passagem passagem = getPassagemPorResultSet(resultSet);
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
            connection = ConexaoBancoDeDados.getConnection();
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
                Passagem passagem = getPassagemPorResultSet(resultSet);
                passagens.add(passagem);
            }
            return passagens;

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

    public boolean inserirIdVenda(Passagem passagem, Venda vendaCriada) throws DatabaseException {
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE PASSAGEM SET ");
            sql.append(" id_venda = ?\n");
            sql.append(" WHERE id_passagem = ? ");

            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());

            preparedStatement.setInt(1, vendaCriada.getIdVenda());
            preparedStatement.setInt(2, passagem.getIdPassagem());

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
}
