package javamos_decolar.com.javamosdecolar.repository;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VendaRepository implements Repository<Venda, Integer> {

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT seq_venda.nextval mysequence from DUAL";
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
    public Venda adicionar(Venda venda) throws DatabaseException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(conexao);
            venda.setIdVenda(proximoId);

            String sql = "INSERT INTO VENDA \n" +
                    "(ID_VENDA, ID_COMPRADOR, ID_COMPANHIA, DATA_VENDA, STATUS, CODIGO)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = conexao.prepareStatement(sql);


            preparedStatement.setInt(1, venda.getIdVenda());
            preparedStatement.setObject(2, venda.getComprador().getIdComprador());
            preparedStatement.setObject(3, venda.getCompanhia().getIdCompanhia());
            preparedStatement.setObject(4, LocalDateTime.now());
            preparedStatement.setString(5, venda.getStatus().name());
            preparedStatement.setString(6, venda.getCodigo());

            preparedStatement.executeUpdate();
            
            return venda;

        } catch (SQLException e) {
            e.printStackTrace();
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

    @Override
    public List<Venda> listar() throws DatabaseException {
        List<Venda> vendas = new ArrayList<>();
        Connection conexao = null;

        try{
            conexao = ConexaoBancoDeDados.getConnection();
            Statement statement = conexao.createStatement();

            String sql = "SELECT * FROM VENDA";

            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                Venda venda = new Venda();
                venda.setIdVenda(resultSet.getInt("id_venda"));
                venda.setComprador((Comprador) resultSet.getObject("comprador"));
                venda.setCompanhia((Companhia) resultSet.getObject("companhia"));
                venda.setData((LocalDateTime) resultSet.getObject("data"));
                venda.setStatus((Status) resultSet.getObject("status"));
                vendas.add(venda);
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
        return vendas;
    }

    @Override
    public boolean editar(Integer id, Venda venda) throws DatabaseException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE VENDA SET ");
            sql.append("id_comprador = ?,");
            sql.append("id_companhia = ?, ");
            sql.append("data_venda = ?");
            sql.append("status = ?");
            sql.append("WHERE id_venda = ?");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());

            statement.setObject(1, venda.getComprador().getIdComprador());
            statement.setObject(2, venda.getCompanhia().getIdCompanhia());
            statement.setObject(3, venda.getData());
            statement.setObject(4, venda.getStatus());
            statement.setInt(5, id);

            int result = statement.executeUpdate();
            return result > 0;

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

    @Override
    public boolean remover(Integer id) throws DatabaseException {
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM COMPANHIA WHERE id_venda = ?";

            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setInt(1, id);

            int result = statement.executeUpdate();
            return result > 0;

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

    public Optional<Venda> getVendaPorCodigo(String codigo) throws DatabaseException {

        Connection conexao = null;

        try{
            conexao = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT p.id_passagem, p.codigo, p.data_partida, p.data_chegada, p.disponivel, p.valor, " +
                    "p.id_passagem,\n" +
                    "v.id_venda, v.codigo as codigo_venda, v.status, v.data_venda,\n" +
                    "c.id_companhia, c.nome_fantasia,\n" +
                    "t.id_trecho, t.origem, t.destino,\n" +
                    "cd.id_comprador\n" +
                    "FROM VENDA v\n" +
                    "INNER JOIN COMPRADOR cd ON cd.id_comprador = v.id_comprador\n" +
                    "INNER JOIN PASSAGEM p ON p.id_venda = v.id_venda \n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia = v.id_companhia\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "WHERE v.codigo = ?";

            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setString(1, codigo);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                Venda venda = getVendaPorResultSet(resultSet);
                return Optional.of(venda);

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

    public boolean cancelarVenda(Integer idVenda) throws DatabaseException  {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE VENDA SET ");
            sql.append("status = ?");
            sql.append("WHERE id_venda = ?");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());

            statement.setString(1, Status.CANCELADO.name());
            statement.setInt(2, idVenda);

            int res = statement.executeUpdate();
            return res > 0;

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

    public List<Venda> getVendasPorCompanhia(Integer idCompanhia) throws DatabaseException {
        List<Venda> vendas = new ArrayList<>();
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT p.id_passagem, p.codigo, p.data_partida, p.data_chegada, p.disponivel, p.valor, " +
                    "p.id_passagem,\n" +
                    "v.id_venda, v.codigo as codigo_venda, v.status, v.data_venda,\n" +
                    "c.id_companhia, c.nome_fantasia,\n" +
                    "t.id_trecho, t.origem, t.destino,\n" +
                    "cd.id_comprador\n" +
                    "FROM VENDA v\n" +
                    "INNER JOIN COMPRADOR cd ON cd.id_comprador = v.id_comprador\n" +
                    "INNER JOIN PASSAGEM p ON p.id_venda = v.id_venda \n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia = v.id_companhia\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "WHERE v.id_companhia = ?";

            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setInt(1, idCompanhia);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Venda venda = getVendaPorResultSet(resultSet);
                vendas.add(venda);
            }
            return vendas;

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

    public List<Venda> getVendasPorComprador(Integer idComprador) throws DatabaseException {
        List<Venda> vendas = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT p.id_passagem, p.codigo, p.data_partida, p.data_chegada, p.disponivel, p.valor, " +
                    "p.id_passagem,\n" +
                    "v.id_venda, v.codigo as codigo_venda, v.status, v.data_venda,\n" +
                    "c.id_companhia, c.nome_fantasia,\n" +
                    "t.id_trecho, t.origem, t.destino,\n" +
                    "cd.id_comprador\n" +
                    "FROM VENDA v\n" +
                    "INNER JOIN COMPRADOR cd ON cd.id_comprador = v.id_comprador\n" +
                    "INNER JOIN PASSAGEM p ON p.id_venda = v.id_venda \n" +
                    "INNER JOIN COMPANHIA c ON c.id_companhia = v.id_companhia\n" +
                    "INNER JOIN TRECHO t ON t.id_trecho = p.id_trecho\n" +
                    "WHERE v.id_comprador = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, idComprador);

            // Executa-se a consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Venda venda = getVendaPorResultSet(resultSet);
                vendas.add(venda);
            }
            return vendas;

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

    private Venda getVendaPorResultSet(ResultSet resultSet) throws SQLException {

        Venda venda = new Venda();
        Companhia companhia = new Companhia();
        Comprador comprador = new Comprador();
        Trecho trecho = new Trecho();
        Passagem passagem = new Passagem();

        venda.setIdVenda(resultSet.getInt("id_venda"));
        venda.setCodigo(resultSet.getString("codigo_venda"));
        venda.setData((resultSet.getTimestamp("data_venda").toLocalDateTime()));

        if (resultSet.getString("status").equals(Status.CANCELADO.name())) {
            venda.setStatus(Status.CANCELADO);
        } else if (resultSet.getString("status").equals(Status.CONCLUIDO.name())) {
            venda.setStatus(Status.CONCLUIDO);
        }

        companhia.setNomeFantasia(resultSet.getString("nome_fantasia"));
        companhia.setIdCompanhia(resultSet.getInt("id_companhia"));
        comprador.setIdComprador(resultSet.getInt("id_comprador"));
        trecho.setIdTrecho(resultSet.getInt("id_trecho"));
        trecho.setOrigem(resultSet.getString("origem"));
        trecho.setDestino(resultSet.getString("destino"));
        passagem.setCodigo(resultSet.getString("codigo"));
        passagem.setDataPartida((resultSet.getTimestamp("data_partida").toLocalDateTime()));
        passagem.setDataChegada((resultSet.getTimestamp("data_chegada").toLocalDateTime()));
        passagem.setValor((resultSet.getBigDecimal("valor")));
        passagem.setIdPassagem(resultSet.getInt("id_passagem"));
        trecho.setCompanhia(companhia);
        passagem.setTrecho(trecho);
        venda.setCompanhia(companhia);
        venda.setComprador(comprador);
        venda.setPassagem(passagem);

        return venda;
    }
}
