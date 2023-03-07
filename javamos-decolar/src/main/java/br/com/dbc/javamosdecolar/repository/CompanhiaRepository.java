package br.com.dbc.javamosdecolar.repository;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.model.Companhia;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class CompanhiaRepository {

    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT seq_companhia.nextval mysequence from DUAL";
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

    public Companhia adicionar(Companhia companhia) throws DatabaseException {
        Connection conexao = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(conexao);
            companhia.setIdCompanhia(proximoId);

            String sql = "INSERT INTO COMPANHIA \n" +
                    "(ID_COMPANHIA, CNPJ, NOME_FANTASIA, ID_USUARIO)\n" +
                    "VALUES(?, ?, LOWER(?), ?)";

            PreparedStatement preparedStatement = conexao.prepareStatement(sql);

            preparedStatement.setInt(1, companhia.getIdCompanhia());
            preparedStatement.setString(2, companhia.getCnpj());
            preparedStatement.setString(3, companhia.getNomeFantasia());
            preparedStatement.setInt(4, companhia.getIdUsuario());

            preparedStatement.executeUpdate();
            return companhia;

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

    public Optional<Companhia> buscaCompanhiaPorNome (String nome) throws DatabaseException {
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT c.ID_COMPANHIA, c.CNPJ, c.NOME_FANTASIA, c.ID_USUARIO, u.LOGIN," +
                    " u.SENHA, u.NOME \n" +
                    "FROM COMPANHIA c \n" +
                    "INNER JOIN USUARIO u \n" +
                    "ON c.ID_USUARIO = u.ID_USUARIO \n" +
                    "WHERE c.NOME_FANTASIA LIKE ?";

            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setString(1, "%" + nome + "%".toLowerCase());

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                Companhia companhiaPesquisa = getCompanhiaPorResultSet(resultSet);
                return Optional.of(companhiaPesquisa);
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

    public Optional<Companhia> buscaCompanhiaPorIdUsuario(Integer idUsuario) throws DatabaseException {
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT ID_COMPANHIA, CNPJ, NOME_FANTASIA, ID_USUARIO FROM COMPANHIA c \n" +
                    "WHERE c.ID_USUARIO = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setInt(1, idUsuario);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                Companhia companhiaPesquisa = getCompanhiaPorResultSet(resultSet);
                return Optional.of(companhiaPesquisa);

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
    
    public List<Companhia> listaCompanhias() throws DatabaseException {
        List<Companhia> companhias = new ArrayList<>();
        Connection conexao = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();
            Statement statement = conexao.createStatement();

            String sql = "SELECT c.ID_COMPANHIA, c.CNPJ, c.NOME_FANTASIA, c.ID_USUARIO, " +
                    "u.LOGIN, u.SENHA, u.NOME \n" +
                    "FROM COMPANHIA c \n" +
                    "INNER JOIN USUARIO u \n" +
                    "ON c.ID_USUARIO = u.ID_USUARIO\n" +
                    "WHERE c.ID_COMPANHIA = ?";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Companhia companhia = getCompanhiaPorResultSet(resultSet);
                companhias.add(companhia);
            }

            return companhias;
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

    public boolean editarCompanhia(Integer idCompanhia, Companhia companhia) throws DatabaseException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE COMPANHIA SET ");
            sql.append("nome_fantasia = ? ");
            sql.append("WHERE ID_COMPANHIA = ?");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());

            statement.setString(1, companhia.getNomeFantasia());
            statement.setInt(2, idCompanhia);
            
            int result = statement.executeUpdate();
            
            return result > 0;

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

    public Optional<Companhia> buscaCompanhiaPorId(Integer id) throws DatabaseException {
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT c.ID_COMPANHIA, c.CNPJ, c.NOME_FANTASIA, c.ID_USUARIO, u.LOGIN, u.SENHA, u.NOME \n" +
                    "FROM COMPANHIA c \n" +
                    "INNER JOIN USUARIO u \n" +
                    "ON c.ID_USUARIO = u.ID_USUARIO " +
                    "WHERE c.ID_COMPANHIA = ?";

            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                Companhia companhiaPesquisa = getCompanhiaPorResultSet(resultSet);
                return Optional.of(companhiaPesquisa);
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

    private Companhia getCompanhiaPorResultSet(ResultSet resultSet) throws SQLException {
        Companhia companhia = new Companhia();

        companhia.setIdCompanhia(resultSet.getInt("id_companhia"));
        companhia.setCnpj(resultSet.getString("cnpj"));
        companhia.setNomeFantasia(resultSet.getString("nome_fantasia"));
        companhia.setIdUsuario(resultSet.getInt("id_usuario"));
        companhia.setLogin(resultSet.getString("login"));
        companhia.setSenha(resultSet.getString("senha"));
        companhia.setNome(resultSet.getString("nome"));

        return companhia;
    }
}