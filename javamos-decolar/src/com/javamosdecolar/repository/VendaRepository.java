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
                    "(ID_VENDA, PASSAGEM, COMPRADOR, COMPANHIA, DATA, STATUS)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = conexao.prepareStatement(sql);

            preparedStatement.setInt(1, venda.getIdVenda());
            preparedStatement.setObject(2, venda.getPassagem());
            preparedStatement.setObject(3, venda.getComprador());
            preparedStatement.setObject(4, venda.getCompanhia());
            preparedStatement.setObject(5, LocalDateTime.now());
            preparedStatement.setString(6, venda.getStatus().name());

            int result = preparedStatement.executeUpdate();
            System.out.println("adicionarVenda.res=" + result);
            return venda;

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
                venda.setPassagem((Passagem) resultSet.getObject("passagem"));
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
            sql.append("passagem = ?,");
            sql.append("comprador = ?,");
            sql.append("companhia = ?, ");
            sql.append("data = ?");
            sql.append("status = ?");
            sql.append("WHERE id_venda = ?");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());

            statement.setObject(1, venda.getPassagem());
            statement.setObject(2, venda.getComprador());
            statement.setObject(3, venda.getCompanhia());
            statement.setObject(4, venda.getData());
            statement.setObject(5, venda.getStatus());
            statement.setInt(6, id);

            int result = statement.executeUpdate();
            System.out.println("editarVenda.res=" + result);

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
            System.out.println("removerVendaPorId.res=" + result);

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

    public Optional<Venda> buscaVendaPorCodigo(String codigo) throws DatabaseException {
        Venda vendaPesquisa = new Venda();
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM VENDA WHERE codigo = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setString(1, codigo);

            ResultSet resultSet = statement.executeQuery(sql);

            vendaPesquisa.setIdVenda(resultSet.getInt("id_comprador"));
            vendaPesquisa.setPassagem((Passagem) resultSet.getObject("passagem"));
            vendaPesquisa.setComprador((Comprador) resultSet.getObject("senha"));
            vendaPesquisa.setCompanhia((Companhia) resultSet.getObject("nome"));
            vendaPesquisa.setData((LocalDateTime) resultSet.getObject("data"));
            vendaPesquisa.setStatus((Status) resultSet.getObject("status"));

            if(resultSet.first()) {
                return Optional.of(vendaPesquisa);
            } else {
                return Optional.empty();
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
            System.out.println("editartrecho.res=" + res);

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

    public List<Venda> buscarVendasPorCompanhia(Integer idCompanhia) throws DatabaseException {
        List<Venda> listaVendaPesquisa = new ArrayList<>();
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM VENDA WHERE id_companhia = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setInt(1, idCompanhia);

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Venda vendaPesquisa = new Venda();
                vendaPesquisa.setIdVenda(resultSet.getInt("id_venda"));
                vendaPesquisa.setCodigo(resultSet.getString("codigo"));
                vendaPesquisa.setPassagem((Passagem) resultSet.getObject("passagem"));
                vendaPesquisa.setComprador((Comprador) resultSet.getObject("comprador"));
                vendaPesquisa.setCompanhia((Companhia) resultSet.getObject("companhia"));
                vendaPesquisa.setData((LocalDateTime) resultSet.getObject("data"));
                vendaPesquisa.setStatus((Status) resultSet.getObject("status"));
                listaVendaPesquisa.add(vendaPesquisa);
            }

            return listaVendaPesquisa;
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

}
