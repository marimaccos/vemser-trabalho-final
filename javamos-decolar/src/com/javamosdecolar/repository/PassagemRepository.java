package javamos_decolar.com.javamosdecolar.repository;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.Passagem;
import javamos_decolar.com.javamosdecolar.model.Companhia;
import javamos_decolar.com.javamosdecolar.model.Status;
import javamos_decolar.com.javamosdecolar.model.Trecho;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

            PreparedStatement prepareStatement = connection.prepareStatement(sql);

            prepareStatement.setInt(1, passagem.getIdPassagem());
            prepareStatement.setString(2, passagem.getCodigo());
            prepareStatement.setTimestamp(3, java.sql.Timestamp.valueOf(passagem.getDataPartida()));
            prepareStatement.setTimestamp(4, java.sql.Timestamp.valueOf(passagem.getDataChegada()));
            prepareStatement.setBoolean(5, passagem.isDisponivel());
            prepareStatement.setBigDecimal(6, passagem.getValor());
            prepareStatement.setInt(7, passagem.getTrecho().getIdTrecho());

            int res = prepareStatement.executeUpdate();
            System.out.println("adicionarPassagem.res=" + res);
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
                    "t.origem, t.destino,\n" +
                    "c.nome_fantasia AS nome_companhia\n" +
                    "FROM PASSAGEM p\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia  = t.id_companhia\n";

            // Executa-se a consulta
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Passagem passagem = getPassagemFromResultSet(resultSet);
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
        //TO-DO
        return false;
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
            System.out.println("removerPassagemPorId.res=" + res);

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

    private Passagem getPassagemFromResultSet(ResultSet resultSet) throws SQLException {
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

    public void mudarDisponibilidadeDaPassagem(boolean disponivel, Passagem passagem) throws DatabaseException {
        // fazer uma edição na passagem pra trocar o status pelo do código passado
        // consultar o enum?
        // ou recebe so o id da passagem para alterar e
        // altera automaticamente ja q so tem dois tipos de disponibilidade?
    }

    public List<Passagem> pegarPassagemPorDataPartida(LocalDate data) throws DatabaseException {
        // TO-DO
        return null;
    }

    public List<Passagem> pegarPassagemPorDataChegada(LocalDate data) throws DatabaseException {
        // TO-DO
        return null;
    }

    public Optional<Passagem> pegarPassagemPorValor(BigDecimal valorMaximo) throws DatabaseException {
        //TO-DO
        return null;
    }

    public List<Passagem> pegarPassagemPorCompanhia(Integer idCompanhia) throws DatabaseException {
        //TO-DO
        return null;
    }

    public Optional<Passagem> pegarPassagemPorCodigo(String codigo) throws DatabaseException {
        //TO-DO
        return null;
    }

    public List<Passagem> pegarUltimasPassagens() throws DatabaseException {
        //TO-DO
        return null;
    }

    public List<Passagem> pegarPassagensPorComprador(Integer idComprador) throws DatabaseException {
        //TO-DO
        return null;
    }

    public Optional<Passagem> pegarPassagemPeloId(Integer indexRemocaoPassagem) throws DatabaseException {
        //TO-DO
        return null;
    }
}
